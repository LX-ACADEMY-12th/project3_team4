<template>
  <div class="guestbook-container">

    <div v-for="data in guestbooks" :key="data.guestBookId" class="mt-0 mb-2 d-flex flex-column">

      <div class="row text-dark align-items-center border border-dark">
        <div class="col-1 small ps-1">No.{{ data.guestBookId }}</div>
        <div class="col-3 small fw-bold">{{ data.nickname }} 🔒</div>
        <div class="col-6 text-muted small ps-0">({{ data.writtenAt }})</div>
        <div class="col-2 text-end">
          <button class="btn btn-sm" @click="goToDelete(data.guestBookId)">| 삭제</button>
        </div>
      </div>

      <div class="row text-dark align-items-center border border-dark">
        <div class="mt-1 mb-2 d-flex border border-dark p-2">
          <div class="me-2">
            <img
              src="https://i.namu.wiki/i/5dbYIx9wSf1tIJGRb5NO8-fgK5YWOCMJA2Q-cEpsOOOFPlxhyqAUhhV5Cm87Pnanhb5-TpoXKFs9P2AZDGjFUQ.webp"
              alt="icon" class="bab" />
          </div>
          <div class="flex-grow-1">
            <p class="mb-1 mt-1 small text-dark">{{ data.guestBookContent }}</p>
          </div>
        </div>
      </div>
    </div>

    <div class="row text-dark align-items-center border border-dark mt-3">
      <div class="col-11 ps-0">
        <input type="text" class="form-control w-100" v-model="newContent" @keyup.enter="goToInsert">
      </div>
      <div class="col-1 ps-0">
        <button class="btn btn-primary" @click="goToInsert()">ok</button>
      </div>
    </div>

    <div class="d-flex justify-content-center align-items-center" style="margin-top: 1em">
      <Pagination :pagination="pagination1" :requestFunc="(page, perPage) => requestGuestBookList(1, page, perPage)" />
    </div>
  </div>
</template>

<script setup>
// Vue의 'Composition API'를 사용하기 위한 필수 라이브러리들을 가져옵니다.
import { ref, onMounted } from 'vue' // 'ref'는 반응형 상태를, 'onMounted'는 컴포넌트 초기화 시 실행되는 훅입니다.
import axios from 'axios' // HTTP 통신을 위한 라이브러리입니다.

// 페이지네이션 컴포넌트와 관련 유틸리티 함수들을 가져옵니다.
import Pagination from '@/components/Pagination.vue'
import { usePagination } from '@/util/pagination'
const { makePagination } = usePagination()

// 컴포넌트의 상태를 관리하는 반응형 변수들을 선언합니다.
const guestbooks = ref([])         // 화면에 표시될 방명록 목록 데이터입니다.
const pagination1 = ref({})        // 페이지네이션 컴포넌트에 필요한 정보를 담을 객체입니다.
const newContent = ref("")         // 사용자가 입력할 새 방명록 내용입니다.
const perPage = 2                  // 🔥 한 페이지에 표시될 방명록 개수를 고정값으로 설정했습니다.
const currentPage = ref(1)         // 현재 사용자가 보고 있는 페이지 번호입니다.

// 컴포넌트가 DOM에 마운트된 직후 호출되는 라이프사이클 훅입니다.
// 페이지가 로드될 때 초기 방명록 데이터를 불러옵니다.
onMounted(() => {
  requestGuestBookList(1, 1, 2)
})

/**
 * 방명록 목록을 서버에서 가져와 화면에 표시하는 비동기 함수입니다.
 * @param {number} guestBookMiniHomeId - 조회할 미니홈피의 고유 ID (현재는 하드코딩)
 * @param {number} page - 요청할 페이지 번호
 * @param {number} perPageFixed - 한 페이지에 표시할 아이템 수
 */
async function requestGuestBookList(guestBookMiniHomeId, page, perPageFixed = perPage) {
  try {
    // Axios GET 요청을 통해 서버에서 전체 방명록 데이터를 가져옵니다.
    const response = await axios.get('http://localhost:8080/api/guestbook-list', {
      params: { guestBookMiniHomeId },
    })

    const allData = response.data     // 서버로부터 받은 전체 데이터 배열입니다.
    const totalCount = allData.length // 전체 방명록 개수입니다.

    // ⚡ 클라이언트 측에서 페이지네이션을 구현하기 위해 배열의 일부를 자릅니다.
    const start = (page - 1) * perPageFixed
    const end = start + perPageFixed
    guestbooks.value = allData.slice(start, end)

    // ⚡ 페이지네이션 컴포넌트에 필요한 정보를 동적으로 생성하여 할당합니다.
    pagination1.value = makePagination({
      page,
      perPage: perPageFixed,
      totalCount
    })

    currentPage.value = page
  } catch (err) {
    // 요청 실패 시 에러를 콘솔에 출력합니다.
    console.error(`에러(list) -> ${err}`)
  }
}

/**
 * 특정 방명록을 삭제하는 비동기 함수입니다.
 * @param {number} guestBookId - 삭제할 방명록의 ID
 */
async function goToDelete(guestBookId) {
  try {
    // Axios POST 요청으로 삭제 API를 호출합니다.
    await axios.post('http://localhost:8080/api/guestbook-delete', { guestBookId })
    // 삭제 성공 후, 현재 페이지의 방명록 리스트를 다시 불러와 화면을 업데이트합니다.
    requestGuestBookList(1, currentPage.value, perPage)
  } catch (err) {
    // 삭제 실패 시 에러를 콘솔에 출력합니다.
    console.error(`에러(delete) -> ${err}`)
  }
}

/**
 * 새로운 방명록을 추가하는 비동기 함수입니다.
 */
async function goToInsert() {
  // 입력 내용이 비어있으면 함수 실행을 중단합니다.
  if (!newContent.value) return

  // 서버에 전송할 데이터 페이로드(payload)를 정의합니다.
  const payload = {
    guestbookContent: newContent.value,
    guestbookMinihomeId: 1, // 하드코딩된 미니홈피 ID
    guestbookWriterId: 3    // 하드코딩된 작성자 ID
  }

  try {
    // Axios POST 요청으로 방명록 추가 API를 호출합니다.
    await axios.post('http://localhost:8080/api/guestbook-insert', payload)
    // 추가 성공 후, 현재 페이지의 방명록 리스트를 다시 불러와 화면을 업데이트합니다.
    requestGuestBookList(1, currentPage.value, perPage)
    // 입력 필드를 초기화합니다.
    newContent.value = ""
  } catch (err) {
    // 추가 실패 시 에러를 콘솔에 출력합니다.
    console.error(`에러(insert) -> ${err}`)
  }
}
</script>

<style scoped>
/* `scoped` 속성은 이 `<style>` 블록의 스타일이 현재 컴포넌트에만 적용되도록 합니다.
  이는 스타일 충돌을 방지합니다.
*/
.bab {
  width: 70px;
  height: 70px;
  object-fit: cover;
}

/* 방명록 컨테이너의 높이를 제한하고 스크롤을 활성화하는 스타일입니다. */
.guestbook-container {
  max-height: 80%;
  /* 부모 요소의 높이를 채우도록 설정 */
  overflow-y: auto;
  /* 내용이 넘칠 때 세로 스크롤바가 생기도록 설정 */
}
</style>
