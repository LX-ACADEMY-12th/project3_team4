<template>
  <div class="guestbook-container">
    <!-- 로딩 상태 -->
    <div v-if="isLoading" class="loading">
      로딩중...
    </div>

    <!-- 방명록 목록 -->
    <div v-else class="guestbook-list">
      <div v-for="data in guestbooks" :key="data.guestBookId" class="guestbook-item">
        <!-- 방명록 헤더 -->
        <div class="guestbook-header">
          <!-- <span class="guestbook-number">No.{{ data.guestBookId }}</span> -->
          <span class="author-name">{{ data.nickname }}</span>
          <span class="write-date">{{ formatDate(data.writtenAt) }}</span>
          <button v-if="canDelete(data)" class="delete-btn" @click="confirmDelete(data.guestBookId)"
            :disabled="isDeleting">
            삭제
          </button>
        </div>

        <!-- 방명록 내용 -->
        <div class="guestbook-content">
          <div class="profile-section">
            <div class="profile-placeholder"></div>
          </div>
          <div class="message-section">
            <p class="message-text">{{ data.guestBookContent }}</p>
          </div>
        </div>
      </div>

      <!-- 데이터가 없을 때 -->
      <div v-if="guestbooks.length === 0" class="empty-state">
        방명록이 없습니다.
      </div>
    </div>

    <!-- 방명록 작성 -->
    <div class="write-form">
      <div class="write-header">방명록 작성</div>

      <div class="write-body">
        <textarea class="write-textarea" v-model="newContent" @keyup.ctrl.enter="goToInsert" placeholder="방명록을 작성해주세요"
          maxlength="500" rows="2"></textarea>

        <button class="submit-btn" @click="goToInsert" :disabled="!newContent.trim() || isSubmitting">
          등록
        </button>
      </div>
    </div>

    <!-- 페이지네이션 -->
    <div v-if="pagination1.totalCount > 0" class="pagination-wrapper">
      <Pagination :pagination="pagination1" :requestFunc="(page, perPage) => requestGuestBookList(page, perPage)" />
    </div>

    <!-- 에러 메시지 -->
    <div v-if="errorMessage" class="error-message">
      {{ errorMessage }}
      <button class="error-close" @click="errorMessage = ''">×</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import axios from 'axios'
import Pagination from '@/components/Pagination.vue'
import { usePagination } from '@/util/pagination'

const { makePagination } = usePagination()

// 반응형 상태
const guestbooks = ref([])
const newContent = ref("")
const currentPage = ref(1)
const perPage = ref(2)

const pagination1 = ref(makePagination({
  page: 1,
  perPage: 2,
  totalCount: 0
}))

// 로딩 상태
const isLoading = ref(false)
const isSubmitting = ref(false)
const isDeleting = ref(false)
const errorMessage = ref("")

// 현재 로그인한 사용자 정보
const loginUserId = sessionStorage.getItem('loginId')
const loginUserPk = sessionStorage.getItem('userId')

// Props
const props = defineProps({
  miniHomeOwnerLoginId: {
    type: String,
    required: true
  }
})

// 컴포넌트 마운트
onMounted(() => {
  console.log('GuestbookView props:', props.miniHomeOwnerLoginId)

  if (!loginUserId) {
    errorMessage.value = "로그인이 필요합니다."
    return
  }

  if (!props.miniHomeOwnerLoginId) {
    errorMessage.value = "미니홈피 정보를 찾을 수 없습니다."
    return
  }

  requestGuestBookList(1, perPage.value)
})

// 💡 watch를 사용하여 miniHomeOwnerLoginId prop의 변경을 감시합니다.
watch(
  () => props.miniHomeOwnerLoginId,
  (newId, oldId) => {
    // ID가 실제로 변경되었을 때만 데이터를 다시 불러옵니다.
    if (newId !== oldId) {
      console.log(`방명록 ID 변경 감지: ${oldId} -> ${newId}`);
      requestGuestBookList(newId, 1, perPage.value);
    }
  },
  { immediate: true } // 💡 컴포넌트가 처음 마운트될 때 즉시 실행합니다.
);

/**
 * 방명록 목록 조회
 */
async function requestGuestBookList(miniHomeOwnerId, page = 1, itemsPerPage = perPage.value) {
  if (isLoading.value) return

  isLoading.value = true
  errorMessage.value = ""

  try {
    const response = await axios.get('http://localhost:8080/api/guestbook-list', {
      params: {
        miniHomeOwnerLoginId: props.miniHomeOwnerLoginId
      },
      timeout: 10000
    })

    const allData = Array.isArray(response.data) ? response.data : []
    const totalCount = allData.length

    // 클라이언트 측 페이지네이션
    const start = (page - 1) * itemsPerPage
    const end = start + itemsPerPage
    guestbooks.value = allData.slice(start, end)

    pagination1.value = makePagination({
      page,
      perPage: itemsPerPage,
      total: totalCount
    })

    currentPage.value = page

  } catch (error) {
    console.error('방명록 조회 오류:', error)
    if (error.code === 'ECONNABORTED') {
      errorMessage.value = '요청 시간이 초과되었습니다.'
    } else if (error.response?.status === 403) {
      errorMessage.value = '접근 권한이 없습니다.'
    } else {
      errorMessage.value = '방명록을 불러오는데 실패했습니다.'
    }
    guestbooks.value = []
  } finally {
    isLoading.value = false
  }
}

/**
 * 방명록 작성
 */
async function goToInsert() {
  if (!newContent.value.trim()) {
    errorMessage.value = "방명록 내용을 입력해주세요."
    return
  }

  if (!loginUserPk) {
    errorMessage.value = "로그인이 필요합니다."
    return
  }

  isSubmitting.value = true
  errorMessage.value = ""

  const payload = {
    guestbookContent: newContent.value.trim(),
    miniHomeOwnerLoginId: props.miniHomeOwnerLoginId, // 임시로 넣은 데이터니까 바꿔줘야함 -> 라우터에서 현재 내가 보고있는 페이지에서의 주인id를 가져옴
    guestbookWriterId: parseInt(loginUserPk) // 이건 로그인 유저 값
  }

  console.log("payload to send:", payload)


  try {
    await axios.post('http://localhost:8080/api/guestbook-insert', payload)
    newContent.value = ""
    await requestGuestBookList(1, perPage.value)
    alert(`방명록 추가 성공!`)
  } catch (error) {
    console.error('방명록 작성 오류:', error)
    errorMessage.value = '방명록 작성에 실패했습니다.'
  } finally {
    isSubmitting.value = false
  }
}

/**
 * 방명록 삭제 확인
 */
function confirmDelete(guestBookId) {
  if (confirm('정말로 이 방명록을 삭제하시겠습니까?')) {
    goToDelete(guestBookId)
  }
}

/**
 * 방명록 삭제
 */
async function goToDelete(guestBookId) {
  if (isDeleting.value) return

  isDeleting.value = true

  try {
    await axios.post('http://localhost:8080/api/guestbook-delete', {
      guestBookId,
      requesterUserId: loginUserPk
    })
    await requestGuestBookList(currentPage.value, perPage.value)
  } catch (error) {
    console.error('방명록 삭제 오류:', error)
    errorMessage.value = '방명록 삭제에 실패했습니다.'
  } finally {
    isDeleting.value = false
  }
}

/**
 * 삭제 권한 체크
 */
function canDelete(guestbookData) {
  return loginUserId === props.miniHomeOwnerLoginId ||
    loginUserPk === guestbookData.guestBookWriterId?.toString()
}

/**
 * 날짜 포맷팅
 */
function formatDate(dateString) {
  if (!dateString) return ''

  try {
    const date = new Date(dateString)
    return date.toLocaleDateString('ko-KR', {
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    })
  } catch (error) {
    console.error('방명록 날짜 포맷팅 오류:', error)
    return dateString
  }
}
</script>

<style scoped>
.guestbook-container {
  width: 100%;
  height: 100%;
  max-height: 100%;
  display: flex;
  flex-direction: column;
  background: #f8f9fa;
  padding: 10px;
  box-sizing: border-box;
  overflow: hidden;
}

.loading {
  text-align: center;
  padding: 20px;
  color: #666;
}

.guestbook-list {
  flex: 1;
  overflow-y: auto;
  margin-bottom: 10px;
}

.guestbook-item {
  background: white;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-bottom: 10px;
}

.guestbook-header {
  background: #f1f3f4;
  padding: 8px 12px;
  border-bottom: 1px solid #ddd;
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 12px;
}

.guestbook-number {
  background: #e9ecef;
  padding: 2px 6px;
  border-radius: 3px;
  font-size: 10px;
}

.author-name {
  font-weight: bold;
}

.write-date {
  color: #666;
  margin-left: auto;
}

.delete-btn {
  background: #dc3545;
  color: white;
  border: none;
  padding: 2px 8px;
  border-radius: 3px;
  font-size: 10px;
  cursor: pointer;
}

.delete-btn:hover:not(:disabled) {
  background: #c82333;
}

.delete-btn:disabled {
  background: #6c757d;
  cursor: not-allowed;
}

.guestbook-content {
  display: flex;
  padding: 12px;
  gap: 10px;
}

.profile-section {
  flex-shrink: 0;
}

.profile-placeholder {
  width: 40px;
  height: 40px;
  background: #dee2e6;
  border-radius: 50%;
}

.message-section {
  flex: 1;
}

.message-text {
  margin: 0;
  line-height: 1.4;
  color: #333;
  font-size: 13px;
  word-break: break-word;
}

.empty-state {
  text-align: center;
  padding: 20px;
  color: #666;
  background: white;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.write-form {
  background: white;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-bottom: 10px;
  flex-shrink: 0;
}

.write-header {
  background: #f1f3f4;
  padding: 8px 12px;
  border-bottom: 1px solid #ddd;
  font-size: 13px;
  font-weight: bold;
}

.write-body {
  padding: 12px;
  display: flex;
  gap: 8px;
}

.write-textarea {
  flex: 1;
  border: 1px solid #ddd;
  border-radius: 3px;
  padding: 8px;
  font-size: 12px;
  font-family: inherit;
  resize: none;
  height: 50px;
}

.write-textarea:focus {
  outline: none;
  border-color: #007bff;
}

.submit-btn {
  background: #007bff;
  border: none;
  color: white;
  padding: 8px 16px;
  border-radius: 3px;
  font-size: 12px;
  cursor: pointer;
  align-self: flex-start;
}

.submit-btn:hover:not(:disabled) {
  background: #0056b3;
}

.submit-btn:disabled {
  background: #6c757d;
  cursor: not-allowed;
}

.pagination-wrapper {
  display: flex;
  justify-content: center;
  flex-shrink: 0;
}

.error-message {
  background: #f8d7da;
  color: #721c24;
  border: 1px solid #f5c6cb;
  border-radius: 4px;
  padding: 8px 12px;
  margin-top: 10px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 12px;
  flex-shrink: 0;
}

.error-close {
  background: none;
  border: none;
  color: #721c24;
  font-size: 16px;
  cursor: pointer;
  padding: 0;
  margin-left: 10px;
}

.error-close:hover {
  color: #000;
}
</style>
