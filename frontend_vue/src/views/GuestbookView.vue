<template>
  <!-- 방명록 전체 -->
  <div class="guestbook-container">


    <!-- 방명록 아이템 반복 -->
    <div v-for="data in guestbooks" :key="data.guestBookId" class="mt-0 mb-2 d-flex flex-column">

      <!-- 헤더 -->
      <div class="row text-dark align-items-center border border-dark">
        <div class="col-1 small ps-1">No.{{ data.guestBookId }}</div>
        <div class="col-3 small fw-bold">{{ data.nickname }} 🔒</div>
        <div class="col-6 text-muted small ps-0">({{ data.writtenAt }})</div>
        <div class="col-2 text-end">
          <button class="btn btn-sm" @click="goToDelete(data.guestBookId)">| 삭제</button>
        </div>
      </div>

      <!-- 본문 -->
      <div class="row text-dark align-items-center border border-dark">
        <div class="mt-1 mb-2 d-flex border border-dark p-2">
          <!-- 아이콘 -->
          <div class="me-2">
            <img
              src="https://i.namu.wiki/i/5dbYIx9wSf1tIJGRb5NO8-fgK5YWOCMJA2Q-cEpsOOOFPlxhyqAUhhV5Cm87Pnanhb5-TpoXKFs9P2AZDGjFUQ.webp"
              alt="icon"
              class="bab"
            />
          </div>
          <!-- 내용 -->
          <div class="flex-grow-1">
            <p class="mb-1 mt-1 small text-dark">{{ data.guestBookContent }}</p>
          </div>
        </div>
      </div>
    </div>

    <!-- 푸터 (입력창) -->
    <div class="row text-dark align-items-center border border-dark mt-3">
      <div class="col-11 ps-0">
        <!-- v-model 연결 -->
        <input 
          type="text" 
          class="form-control w-100" 
          v-model="newContent"
          @keyup.enter="goToInsert"
        >
      </div>
      <div class="col-1 ps-0">
        <button class="btn btn-primary" @click="goToInsert()">ok</button>
      </div>
    </div>

    <!-- 페이지 -->
    <div class="d-flex justify-content-center align-items-center" style="margin-top: 1em">
      <Pagination
        :pagination="pagination1"
        :requestFunc="(page, perPage) => requestGuestBookList(1, page, perPage)"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

// 페이지네이션
import Pagination from '@/components/Pagination.vue'
import { usePagination } from '@/util/pagination'
const { makePagination } = usePagination()

// 상태
const guestbooks = ref([])
const pagination1 = ref({})
const newContent = ref("")
const perPage = 2            // 🔥 한 화면에 2개 고정
const currentPage = ref(1)   // 현재 페이지

onMounted(() => {
  requestGuestBookList(1, 1, 2)
})

// 리스트 조회 (프론트에서 slice)
async function requestGuestBookList(guestBookMiniHomeId, page, perPageFixed = perPage) {
  try {
    const response = await axios.get('http://localhost:8080/api/guestbook-list', {
      params: { guestBookMiniHomeId },
    })

    const allData = response.data        // 전체 데이터 배열
    const totalCount = allData.length    // 전체 개수

    // ⚡ 페이지네이션 slice
    const start = (page - 1) * perPageFixed
    const end = start + perPageFixed
    guestbooks.value = allData.slice(start, end)

    // ⚡ 페이지네이션 정보 세팅
    pagination1.value = makePagination({
      page,
      perPage: perPageFixed,
      totalCount
    })

    currentPage.value = page
  } catch (err) {
    console.error(`에러(list) -> ${err}`)
  }
}

// 삭제
async function goToDelete(guestBookId) {
  try {
    await axios.post('http://localhost:8080/api/guestbook-delete', { guestBookId })
    requestGuestBookList(1, currentPage.value, perPage)   // 삭제 후 현재 페이지 유지
  } catch (err) {
    console.error(`에러(delete) -> ${err}`)
  }
}

// 추가
async function goToInsert() {
  if (!newContent.value) return

  const payload = {
    guestbookContent: newContent.value,
    guestbookMinihomeId: 1,
    guestbookWriterId: 3
  }

  try {
    await axios.post('http://localhost:8080/api/guestbook-insert', payload)
    requestGuestBookList(1, currentPage.value, perPage)   // 추가 후 현재 페이지 유지
    newContent.value = ""
  } catch (err) {
    console.error(`에러(insert) -> ${err}`)
  }
}
</script>

<style scoped>
.bab {
  width: 70px;
  height: 70px;
  object-fit: cover;
}

/* 🔥 방명록 전체 높이 제한 */
.guestbook-container {
  max-height: 80%;       /* 부모 h-100 채우기 */
  overflow-y: auto;       /* 넘치면 스크롤 */
}
</style>

