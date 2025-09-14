<template>
  <div
    class="d-flex flex-column p-3"
    :style="{
      'font-family': 'DotGothic16, sans-serif',
      height: '700px',
      width: '900px',
      'background-color': userInfo.backgroundColor,
    }"
  >
    <!-- 상단 헤더 -->
    <div class="d-flex p-2 mb-3 text-black justify-content-between align-items-center">
      <div class="d-flex flex-column ms-4 me-4 align-items-center border border-dark">
        <span class="m-3">{{ userInfo.nickname || '사용자' }}님의 미니홈피</span>
        <div class="border border-dark">
          <span class="badge bg-danger me-1">TODAY {{ visitCount?.todayCount || '13' }}</span>
          <span class="badge bg-secondary">TOTAL {{ visitCount?.totalCount || '13' }}</span>
        </div>
      </div>

      <div class="border border-dark col-8 mx-auto">
        <img src="https://via.placeholder.com/100x30/007bff/ffffff?text=CYWORLD" alt="CYWORLD" />
      </div>

      <div>
        <button class="btn btn-sm btn-outline-info" @click="toggleEdit">
          {{ isEditing ? '저장' : '수정' }}
        </button>
      </div>
    </div>

    <div class="d-flex flex-fill flex-row">
      <!-- 왼쪽 영역 -->
      <div class="d-flex flex-column bg-white p-2 me-2 col-3 sidebar-left">
        <!-- 프로필 -->
        <div class="d-flex flex-column align-items-center mb-2 border border-dark profile-box">
          <div class="w-100 h-100">
            <div v-if="isEditing">
              <input type="file" @change="onFileChange" class="form-control form-control-sm mb-1" />
              <img v-if="previewImage" :src="previewImage" class="profile-img" />
            </div>
            <img
              v-else
              :src="userInfo.profileImage || 'https://via.placeholder.com/120x120/cccccc/ffffff?text=Profile'"
              class="profile-img"
            />
          </div>
        </div>

        <!-- 기분 -->
        <div class="text-center mb-1 small">
          <div v-if="isEditing">
            <select v-model="userInfo.todayMood" class="form-select form-select-sm">
              <option value="">[기분 선택]</option>
              <option>😊 행복</option>
              <option>😢 슬픔</option>
              <option>😡 화남</option>
              <option>😴 피곤</option>
            </select>
          </div>
          <div v-else class="text-muted">TODAY IS {{ userInfo.todayMood || '[기분]' }}</div>
        </div>

        <!-- 생일 + 성별 -->
        <div class="text-center mb-1 small">
          <div v-if="isEditing" class="d-flex gap-1">
            <input type="date" v-model="userInfo.birthDate" class="form-control form-control-sm" />
            <select v-model="userInfo.gender" class="form-select form-select-sm" style="max-width: 70px;">
              <option value="">성별</option>
              <option>남자</option>
              <option>여자</option>
            </select>
          </div>
          <div v-else>
            생일: {{ userInfo.birthDate || '등록 안 됨' }}
            <span v-if="userInfo.gender"> / {{ userInfo.gender }}</span>
          </div>
        </div>

        <!-- 지역 -->
        <div class="text-center mb-1 small">
          <div v-if="isEditing">
            <input
              type="text"
              v-model="userInfo.region"
              class="form-control form-control-sm"
              placeholder="지역 입력"
            />
          </div>
          <div v-else>지역: {{ userInfo.region || '등록 안 됨' }}</div>
        </div>

        <!-- 취미 -->
        <div class="text-center mb-2 small">
          <div v-if="isEditing">
            <select v-model="userInfo.hobby" class="form-select form-select-sm">
              <option value="">[취미 선택]</option>
              <option>독서</option>
              <option>운동</option>
              <option>음악</option>
              <option>여행</option>
              <option>게임</option>
            </select>
          </div>
          <div v-else>취미: {{ userInfo.hobby || '등록 안 됨' }}</div>
        </div>

        <!-- 🎨 배경색 선택 -->
        <div class="text-center mb-2 small">
          <div v-if="isEditing">
            <select v-model="userInfo.backgroundColor" class="form-select form-select-sm">
              <option value="#f8f9fa">기본 (연회색)</option>
              <option value="#cce5ff">파랑</option>
              <option value="#fddde6">분홍</option>
              <option value="#212529">검정</option>
            </select>
          </div>
          <div v-else>
            배경색:
            <span
              class="d-inline-block"
              :style="{ backgroundColor: userInfo.backgroundColor, width: '40px', height: '15px', border: '1px solid #000' }"
            ></span>
          </div>
        </div>

        <!-- 테마 선택 -->
        <div class="text-center mb-2 small">
          <div v-if="isEditing">
            <select v-model="userInfo.backgroundColor" class="form-select form-select-sm">
              <option value="#f8f9fa">심플(기본)</option>
              <option value="#cce5ff">귀여운</option>
              <option value="#fddde6">세련된</option>
              <option value="#212529">빈티지</option>
            </select>
          </div>
          <div v-else>
            테마:
            <span
              class="d-inline-block"
              :style="{ backgroundColor: userInfo.theme, width: '40px', height: '15px', border: '1px solid #000' }"
            ></span>
          </div>
        </div>

        <!-- 상태 메시지 -->
        <div class="d-flex flex-column align-items-center mb-2 border border-dark text-center status-box">
          <p
            v-if="!isEditing"
            class="small w-100 m-0 d-flex align-items-center justify-content-center h-100"
          >
            {{ userInfo.statusMessage || '사용자 작성 멘트' }}
          </p>
          <textarea
            v-else
            v-model="userInfo.statusMessage"
            class="form-control form-control-sm h-100"
          ></textarea>
        </div>

        <!-- 홈주인 -->
        <div class="border border-dark">
          <div class="d-flex align-items-center">
            <span class="small me-1">홈주인</span>
            <span class="small">{{ userInfo.nickname || '나' }}</span>
          </div>
        </div>
      </div>

      <!-- 오른쪽 -->
      <div class="d-flex flex-grow-1 flex-column col-9 border border-black">
        <!-- 상단 -->
        <div class="d-flex border border-dark h-25">
          <div class="d-flex col-8 justify-content-center align-items-center">
            <span class="w-100 text-center">{{ userInfo.emptySpaceText || '빈공간' }}</span>
          </div>

          <div class="d-flex flex-grow-1 p-2" v-if="userInfo.youtubeVideoId">
            <iframe
              :src="`https://www.youtube.com/embed/${userInfo.youtubeVideoId}`"
              frameborder="0"
              style="width: 100%; height: 100%"
              allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
              allowfullscreen
            ></iframe>
          </div>
          <div class="d-flex flex-grow-1 p-2 justify-content-center align-items-center bg-light" v-else>
            <span class="text-muted">동영상 없음</span>
          </div>
        </div>

        <!-- 메인 -->
        <div class="d-flex flex-grow-1 p-1 justify-content-center align-items-center border border-dark">
          <div class="d-flex col-11 justify-content-center align-items-center h-100 border border-dark">
            <div v-if="activeTab === 'home'" class="w-100 h-100 d-flex justify-content-center align-items-center">
              <img
                :src="userInfo.miniroomImage || 'https://img1.daumcdn.net/thumb/R720x0.q80/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F9938F0375BBEF5CC21'"
                alt="미니룸"
                class="img-fluid"
              />
            </div>

            <div v-else-if="activeTab === 'guestbook'" class="w-100 h-100 p-3 overflow-auto">
              <GuestbookView />
            </div>

            <div v-else-if="activeTab === 'photos'" class="w-100 h-100 p-3 overflow-auto">
              <h6>사진첩</h6>
              <div class="row">
                <div v-for="photo in photosList" :key="photo.id" class="col-4 mb-2">
                  <img :src="photo.url" :alt="photo.title" class="img-fluid border" @click="viewPhoto(photo)" />
                  <small class="d-block text-center">{{ photo.title }}</small>
                </div>
                <div v-if="photosList.length === 0" class="text-center text-muted w-100">사진이 없습니다</div>
              </div>
            </div>
          </div>

          <!-- 탭 -->
          <div class="d-flex flex-column h-100 flex-grow-1 justify-content-start">
            <div class="d-flex flex-column flex-grow-1 justify-content-start">
              <ul class="nav nav-tabs flex-column">
                <li class="nav-item border border-dark">
                  <a class="nav-link" :class="{ active: activeTab === 'home' }" href="#" @click.prevent="activeTab = 'home'">홈</a>
                </li>
                <li class="nav-item border border-dark">
                  <a class="nav-link" :class="{ active: activeTab === 'guestbook' }" href="#" @click.prevent="changeTab('guestbook')">방명록</a>
                </li>
                <li class="nav-item border border-dark">
                  <a class="nav-link" :class="{ active: activeTab === 'photos' }" href="#" @click.prevent="changeTab('photos')">사진첩</a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import axios from 'axios'
import GuestbookView from './GuestbookView.vue'

export default {
  components: { GuestbookView },
  name: 'MiniHomepage',
  data() {
    return {
      isEditing: false,
      previewImage: null,
      userInfo: {
        userId: null,
        nickname: null,
        profileImage: null,
        todayMood: null,
        statusMessage: null,
        birthDate: null,
        gender: null,
        region: null,
        hobby: null,
        youtubeVideoId: null, // 서버에서 아직 안 주면 null 유지
        backgroundColor: '#f8f9fa',
        theme: null,          // appliedThemeId를 잠시 theme에 꽂아줌
      },
      visitCount: { todayCount: 0, totalCount: 0 },
      //api/showMiniHome 는 숫자 userId 필요
      loginUserPk: 3, // 임시: 실제 로그인 후 userId로 교체
      friendsList: [],
      guestbookList: [],
      photosList: [],
      activeTab: 'home',
    }
  },
  mounted() {
    // 최초 렌더링 시 서버에서 화면용 DTO 받기
    this.fetchMinihome(this.loginUserPk)
  },
  methods: {
    // 미니홈피 화면 데이터를 띄울거임
    async fetchMinihome(userId) {
      try {
        const { data } = await axios.get('http://localhost:8080/api/showMiniHome', {
          params: { userId },
        })
        
        console.log('서버에서 받은 데이터:', data)  // 확인용
        // 서버 DTO 그대로 매핑
        this.userInfo = {
          userId: data.userId,
          nickname: data.nickname,
          profileImage: data.profileImage,
          todayMood: data.todayMood,
          statusMessage: data.statusMessage,
          birthDate: data.birthDate,
          gender: data.gender,
          region: data.region,
          hobby: data.hobby,
          youtubeVideoId: data.youtubeVideoId || null,
          backgroundColor: data.backgroundColor || '#f8f9fa',
          theme: data.appliedThemeId ?? null, // 현재 select는 theme에 묶여있음
        }

        // 방문자수 묶음 처리 (visitCount 객체가 오면 그걸 쓰고, 아니면 today/total 합성)
        this.visitCount = data.visitCount
          ? data.visitCount
          : { todayCount: data.todayCount || 0, totalCount: data.totalCount || 0 }
      } catch (error) {
        console.error('미니홈피 정보를 가져오는데 실패했습니다.', error)
      }
    },

    async toggleEdit() {
  if (this.isEditing) {
    try {
      console.log('요청 URL:', 'http://localhost:8080/api/minihome-update')
      console.log('전송 데이터:', this.userInfo)
      
      const response = await axios.post('http://localhost:8080/api/minihome-update', this.userInfo)
      
      console.log('응답:', response.data)
      if (response.data > 0) {
        alert('저장 완료')
        this.isEditing = false
      }     
    } catch (error) {
      console.error('전체 에러:', error)
      console.error('응답 에러:', error.response)
      alert('저장 실패')
    }
  } else {
    this.isEditing = true
  }
},

    onFileChange(e) {
      const file = e.target.files[0]
      if (file) {
        const reader = new FileReader()
        reader.onload = (e) => {
          this.userInfo.profileImage = e.target.result         
        }
        reader.readAsDataURL(file)
      }
    },
    changeTab(tabName) {
      this.activeTab = tabName
    },
  },
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=DotGothic16&display=swap');

.nav-tabs .nav-link {
  border-radius: 0;
  font-size: 0.875rem;
}

.nav-tabs .nav-link.active {
  background-color: #007bff;
  color: white;
  border-color: #007bff;
}

.badge {
  font-size: 0.75rem;
}

.sidebar-left {
  font-size: 0.8rem;
  line-height: 1.2;
}

.profile-box {
  width: 100%;
  height: 150px;
  margin-bottom: 8px;
  overflow: hidden;
}

.status-box {
  width: 100%;
  height: 60px;
}

.profile-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.form-control-sm,
.form-select-sm,
textarea.form-control-sm {
  font-size: 0.8rem;
  padding: 2px 6px;
}

textarea.form-control-sm {
  resize: none;
}
</style>
