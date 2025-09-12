<template>
  <div class="minihompy-wrapper">
    <div class="cyworld-container">

      <!-- 메인 미니홈피 영역 -->
      <div class="main-area">
        <div class="hompy-frame">

          <!-- 상단 헤더 -->
          <div class="header">
            <div class="visitor-counter">
              TODAY 5명 | TOTAL 123명
            </div>
            <div class="title">
              {{ nickname }}님의 미니홈피
            </div>
          </div>

          <!-- 메인 컨텐츠 -->
          <div class="content">
            <div class="sidebar">
              <div class="profile">
                <div class="mood">TODAY IS 😊</div>
                <div class="photo">프로필 사진</div>
                <div class="message">안녕하세요!</div>
                <div class="info">{{ nickname }} / 25세</div>
                <div class="friends">일촌 & 파도타기</div>
              </div>
            </div>

            <!-- 메인 컨텐츠 + 우측 탭 -->
            <div class="main-content-wrapper">

              <!-- 미니룸 -->
              <div class="miniroom">
                미니룸 영역 (미니미)
              </div>

              <!-- 탭 컨텐츠 + 우측 탭 버튼 -->
              <div class="tab-container">

                <!-- 컨텐츠 영역 -->
                <div class="tab-content-area">
                  <!-- 사진첩 -->
                  <div v-if="activeTab === 'photos'" class="photos-content">
                    <div class="content-header">
                      <h5>📷 사진첩</h5>
                      <button v-if="isOwner" class="btn-upload">사진 올리기</button>
                    </div>

                    <div class="photo-grid">
                      <div class="photo-item" v-for="i in 6" :key="i">
                        <div class="photo-thumb">사진{{ i }}</div>
                      </div>
                    </div>

                    <div class="no-content" v-if="photos.length === 0">
                      <i class="icon">📷</i>
                      <p>사진이 없습니다</p>
                    </div>
                  </div>

                  <!-- 방명록 -->
                  <div v-if="activeTab === 'guestbook'" class="guestbook-content">
                    <div class="content-header">
                      <h5>📖 방명록</h5>
                      <span class="count">총 {{ guestbookCount }}개</span>
                    </div>

                    <!-- 방명록 작성 -->
                    <div class="guestbook-write">
                      <textarea v-model="newMessage" placeholder="방명록을 남겨보세요..." rows="3"></textarea>
                      <button @click="writeGuestbook">확인</button>
                    </div>

                    <!-- 방명록 목록 -->
                    <div class="guestbook-list">
                      <div class="guestbook-item" v-for="item in guestbookList" :key="item.id">
                        <div class="item-header">
                          <strong>{{ item.writer }}</strong>
                          <span class="date">{{ item.date }}</span>
                        </div>
                        <div class="item-content">{{ item.content }}</div>
                      </div>
                    </div>

                    <div class="no-content" v-if="guestbookList.length === 0">
                      <i class="icon">📖</i>
                      <p>방명록이 없습니다</p>
                    </div>
                  </div>
                </div>

                <!-- 우측 탭 버튼들 -->
                <div class="tab-buttons-right">
                  <div class="tab-button" :class="{ active: activeTab === 'photos' }" @click="activeTab = 'photos'">
                    <div class="tab-icon">📷</div>
                    <div class="tab-text">사진첩</div>
                  </div>

                  <div class="tab-button" :class="{ active: activeTab === 'guestbook' }"
                    @click="activeTab = 'guestbook'">
                    <div class="tab-icon">📖</div>
                    <div class="tab-text">방명록</div>
                  </div>
                </div>

              </div>
            </div>
          </div>

        </div>
      </div>

      <!-- 우측 사이드바 -->
      <div class="right-sidebar">
        <div class="bgm">
          🎵 BGM 영역
        </div>
        <div class="buttons">
          <button>내 미니홈피</button>
          <button>로그아웃</button>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const nickname = ref('홍길동')
const activeTab = ref('photos')
const isOwner = ref(true)
const photos = ref([])
const guestbookCount = ref(5)
const newMessage = ref('')

const guestbookList = ref([
  { id: 1, writer: '친구1', content: '안녕하세요! 놀러왔어요~', date: '12-25' },
  { id: 2, writer: '친구2', content: '미니홈피 예쁘네요!', date: '12-24' },
  { id: 3, writer: '친구3', content: '메리크리스마스!', date: '12-24' }
])

const writeGuestbook = () => {
  if (newMessage.value.trim()) {
    guestbookList.value.unshift({
      id: Date.now(),
      writer: '방문자',
      content: newMessage.value,
      date: new Date().toLocaleDateString('ko-KR', { month: 'short', day: 'numeric' })
    })
    newMessage.value = ''
    guestbookCount.value++
  }
}
</script>

<style scoped>
.minihompy-wrapper {
  width: 100vw;
  height: 100vh;
  background: #E6F3FF;
  display: flex;
  align-items: center;
  justify-content: center;
}

.cyworld-container {
  width: 95%;
  height: 95%;
  display: flex;
  gap: 20px;
}

.main-area {
  flex: 3;
  background: #90EE90;
  border-radius: 15px;
  padding: 20px;
}

.hompy-frame {
  width: 100%;
  height: 100%;
  background: white;
  border-radius: 10px;
  padding: 15px;
  display: flex;
  flex-direction: column;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border-bottom: 2px solid #eee;
  margin-bottom: 15px;
}

.visitor-counter {
  background: #FFE4E1;
  padding: 5px 10px;
  border: 1px solid #FF69B4;
  border-radius: 5px;
  font-size: 12px;
  color: #8B008B;
  font-weight: bold;
}

.title {
  font-size: 24px;
  font-weight: bold;
  color: #333;
}

.content {
  flex: 1;
  display: flex;
  gap: 15px;
}

.sidebar {
  width: 180px;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 10px;
}

.profile>div {
  margin-bottom: 10px;
  padding: 8px;
  border: 1px solid #eee;
  border-radius: 3px;
  text-align: center;
  font-size: 13px;
}

.photo {
  height: 120px;
  background: #f5f5f5;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
}

.main-content-wrapper {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.miniroom {
  height: 120px;
  background: #E8F4FD;
  border: 2px solid #4169E1;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #666;
  font-weight: bold;
}

/* 탭 컨테이너 - 우측 탭 형식 */
.tab-container {
  flex: 1;
  display: flex;
  border: 2px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
  background: white;
}

.tab-content-area {
  flex: 1;
  padding: 15px;
  overflow-y: auto;
}

/* 우측 탭 버튼들 */
.tab-buttons-right {
  width: 80px;
  background: #f8f9fa;
  border-left: 2px solid #ddd;
  display: flex;
  flex-direction: column;
}

.tab-button {
  height: 100px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  border-bottom: 1px solid #ddd;
  transition: all 0.2s;
  background: #f8f9fa;
  color: #666;
}

.tab-button:last-child {
  border-bottom: none;
}

.tab-button:hover {
  background: #e9ecef;
}

.tab-button.active {
  background: white;
  color: #4169E1;
  border-left: 3px solid #4169E1;
  margin-left: -2px;
}

.tab-icon {
  font-size: 20px;
  margin-bottom: 5px;
}

.tab-text {
  font-size: 11px;
  font-weight: bold;
  writing-mode: vertical-rl;
  text-orientation: mixed;
}

/* 컨텐츠 스타일 */
.content-header {
  display: flex;
  justify-content: between;
  align-items: center;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}

.content-header h5 {
  margin: 0;
  color: #333;
}

.count {
  font-size: 12px;
  color: #666;
}

.btn-upload {
  padding: 5px 10px;
  background: #4169E1;
  color: white;
  border: none;
  border-radius: 3px;
  font-size: 12px;
  cursor: pointer;
}

/* 사진첩 스타일 */
.photo-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(80px, 1fr));
  gap: 8px;
}

.photo-item {
  aspect-ratio: 1;
}

.photo-thumb {
  width: 100%;
  height: 100%;
  background: #f0f0f0;
  border: 1px solid #ddd;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 10px;
  color: #999;
  cursor: pointer;
}

.photo-thumb:hover {
  background: #e0e0e0;
}

/* 방명록 스타일 */
.guestbook-write {
  background: #f8f9fa;
  padding: 10px;
  border-radius: 5px;
  margin-bottom: 15px;
}

.guestbook-write textarea {
  width: 100%;
  border: 1px solid #ddd;
  border-radius: 3px;
  padding: 8px;
  font-size: 12px;
  resize: vertical;
  margin-bottom: 8px;
}

.guestbook-write button {
  padding: 5px 15px;
  background: #4169E1;
  color: white;
  border: none;
  border-radius: 3px;
  font-size: 12px;
  cursor: pointer;
  float: right;
}

.guestbook-list {
  max-height: 250px;
  overflow-y: auto;
}

.guestbook-item {
  padding: 10px;
  border-bottom: 1px solid #eee;
  margin-bottom: 8px;
}

.guestbook-item:last-child {
  border-bottom: none;
}

.item-header {
  display: flex;
  justify-content: between;
  align-items: center;
  margin-bottom: 5px;
}

.item-header strong {
  font-size: 13px;
  color: #333;
}

.date {
  font-size: 11px;
  color: #999;
}

.item-content {
  font-size: 12px;
  color: #555;
  line-height: 1.4;
}

.no-content {
  text-align: center;
  padding: 40px 20px;
  color: #999;
}

.no-content .icon {
  font-size: 40px;
  margin-bottom: 10px;
  display: block;
}

/* 우측 사이드바 */
.right-sidebar {
  width: 250px;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.bgm {
  height: 200px;
  background: #6c757d;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 10px;
  font-weight: bold;
}

.buttons {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.buttons button {
  padding: 15px;
  border: 1px solid #ddd;
  background: white;
  border-radius: 5px;
  cursor: pointer;
  font-weight: bold;
}

.buttons button:hover {
  background: #f8f9fa;
}

/* 스크롤바 스타일 */
.tab-content-area::-webkit-scrollbar,
.guestbook-list::-webkit-scrollbar {
  width: 6px;
}

.tab-content-area::-webkit-scrollbar-track,
.guestbook-list::-webkit-scrollbar-track {
  background: #f1f1f1;
}

.tab-content-area::-webkit-scrollbar-thumb,
.guestbook-list::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 3px;
}
</style>
