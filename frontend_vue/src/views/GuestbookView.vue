<template>
  <!-- 방명록 전체 -->
  <div class="d-flex flex-column border border-dark p-3 mb-3" style="width: 800px;">
    
    <!-- 방명록 아이템 반복 -->
    <div v-for="data in guestbooks" :key="data.guestBookId" class="mb-3">

      <!-- 헤더 -->
      <div class="row text-dark align-items-center border border-dark" style="height: 50px;">
        <div class="col-1 small">No.{{ data.guestBookId }}</div>
        <div class="col-1 fw-bold">{{ data.nickname }}</div>
        <div class="col-1 ps-0">🔒</div>
        <div class="col-5 text-muted small">({{ data.writtenAt }})</div>
        <div class="col-4 text-end">
          <button class="btn btn-sm btn-danger" @click="goToDelete(data.guestBookId)">삭제</button>
        </div>
      </div>

      <!-- 본문 -->
      <div class="row text-dark align-items-center border border-dark">
        <div class="mt-2 mb-3 d-flex border border-dark p-3">
          <!-- 아이콘 -->
          <div class="me-3">
            <img
              src="https://i.namu.wiki/i/5dbYIx9wSf1tIJGRb5NO8-fgK5YWOCMJA2Q-cEpsOOOFPlxhyqAUhhV5Cm87Pnanhb5-TpoXKFs9P2AZDGjFUQ.webp"
              alt="icon"
              class="bab"
            />
          </div>
          <!-- 내용 -->
          <div>
            <p class="mb-0 mt-3 text-dark">{{ data.guestBookContent }}</p>
          </div>
        </div>
      </div>
    </div>

    <!-- 푸터 (입력창) -->
    <div class="row text-dark align-items-center border border-dark mt-3">
      <div class="col-11">
        <!-- v-model 연결 -->
        <input 
          type="text" 
          class="form-control w-100" 
          v-model="newContent"
          @keyup.enter="goToInsert"
        >
      </div>
      <div class="col-1">
        <!-- data 넘기지 말고 그냥 호출 -->
        <button class="btn btn-primary" @click="goToInsert()">ok</button>
      </div>
    </div>

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const guestbooks = ref([])
const newContent = ref("")

onMounted(() => {
  requestGuestBookList(1)
})

// 리스트 조회
async function requestGuestBookList(guestBookMiniHomeId) {
  try {
    const response = await axios.get('http://localhost:8080/api/guestbook-list', {
      params: { guestBookMiniHomeId }
    })
    guestbooks.value = response.data
  } catch (err) {
    console.error(`에러(list) -> ${err}`)
  }
}

// 삭제
async function goToDelete(guestBookId) {
  try {
    await axios.post('http://localhost:8080/api/guestbook-delete', { guestBookId })
    requestGuestBookList(1)
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
    requestGuestBookList(1)
    newContent.value = "" // 입력창 비우기
  } catch (err) {
    console.error(`에러(insert) -> ${err}`)
  }
}
</script>

<style scoped>
.bab {
  width: 120px;
  height: 100px;
}
</style>
