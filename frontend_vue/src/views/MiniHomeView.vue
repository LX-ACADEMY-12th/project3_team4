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
        <button class="btn btn-sm btn-outline-info" @click="toggleEdit" :disabled="isSaving">
          {{ isSaving ? '저장중...' : (isEditing ? '저장' : '수정') }}
        </button>
      </div>
    </div>

    <!-- 저장 성공/실패 메시지 -->
    <div v-if="saveMessage" class="alert alert-dismissible fade show" :class="saveMessageClass" role="alert">
      {{ saveMessage }}
      <button type="button" class="btn-close" @click="saveMessage = ''"></button>
    </div>

    <div class="d-flex flex-fill flex-row">
      <!-- 왼쪽 영역 -->
      <div class="d-flex flex-column bg-white p-2 me-2 col-3 sidebar-left">
        <!-- 프로필 -->
        <div class="d-flex flex-column align-items-center mb-2 border border-dark profile-box">
          <div class="w-100 h-100">
            <div v-if="isEditing">
              <input type="file" @change="onFileChange" class="form-control form-control-sm mb-1" accept="image/*" />
              <img v-if="previewImage" :src="previewImage" class="profile-img" />
              <img v-else-if="userInfo.profileImage" :src="userInfo.profileImage" class="profile-img" />
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
              <option value="😊 행복">😊 행복</option>
              <option value="😢 슬픔">😢 슬픔</option>
              <option value="😡 화남">😡 화남</option>
              <option value="😴 피곤">😴 피곤</option>
              <option value="😍 설렘">😍 설렘</option>
              <option value="🤔 고민중">🤔 고민중</option>
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
              <option value="남자">남자</option>
              <option value="여자">여자</option>
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
              maxlength="50"
            />
          </div>
          <div v-else>지역: {{ userInfo.region || '등록 안 됨' }}</div>
        </div>

        <!-- 취미 -->
        <div class="text-center mb-2 small">
          <div v-if="isEditing">
            <select v-model="userInfo.hobby" class="form-select form-select-sm">
              <option value="">[취미 선택]</option>
              <option value="독서">독서</option>
              <option value="운동">운동</option>
              <option value="음악">음악</option>
              <option value="여행">여행</option>
              <option value="게임">게임</option>
              <option value="요리">요리</option>
              <option value="영화감상">영화감상</option>
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
              <option value="#d4edda">연두</option>
              <option value="#fff3cd">노랑</option>
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
            <select v-model="userInfo.theme" class="form-select form-select-sm">
              <option value="1">심플(기본)</option>
              <option value="2">귀여운</option>
              <option value="3">세련된</option>
              <option value="4">빈티지</option>
            </select>
          </div>
          <div v-else>
            테마: {{ getThemeName(userInfo.theme) }}
          </div>
        </div>

        <!-- YouTube 비디오 ID -->
        <div class="text-center mb-2 small" v-if="isEditing">
          <input
            type="text"
            v-model="userInfo.youtubeVideoId"
            class="form-control form-control-sm"
            placeholder="YouTube 비디오 ID"
            maxlength="20"
          />
          <small class="text-muted">예: dQw4w9WgXcQ</small>
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
            placeholder="상태 메시지를 입력하세요"
            maxlength="200"
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
      isSaving: false,
      previewImage: null,
      saveMessage: '',
      saveMessageClass: '',
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
        youtubeVideoId: null,
        backgroundColor: '#f8f9fa',
        theme: null,
      },
      // 수정 전 원본 데이터 백업 (취소 기능을 위해)
      originalUserInfo: {},
      visitCount: { todayCount: 0, totalCount: 0 },
      loginUserPk: 3, // 임시: 실제 로그인 후 userId로 교체
      friendsList: [],
      guestbookList: [],
      photosList: [],
      activeTab: 'home',
    }
  },
  mounted() {
    this.fetchMinihome(this.loginUserPk)
  },
  methods: {
    // 미니홈피 화면 데이터 조회
    async fetchMinihome(userId) {
      try {
        const { data } = await axios.get('http://localhost:8080/api/showMiniHome', {
          params: { userId },
        })

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
          theme: data.appliedThemeId ?? null,
        }

        this.visitCount = data.visitCount
          ? data.visitCount
          : { todayCount: data.todayCount || 0, totalCount: data.totalCount || 0 }
      } catch (error) {
        console.error('미니홈피 정보를 가져오는데 실패했습니다.', error)
        this.showMessage('미니홈피 정보를 불러오는데 실패했습니다.', 'error')
      }
    },

    // 수정/저장 토글
    async toggleEdit() {
      if (this.isEditing) {
        // 저장 버튼 클릭 시
        await this.saveMinihome()
      } else {
        // 수정 버튼 클릭 시 - 원본 데이터 백업
        this.originalUserInfo = { ...this.userInfo }
        this.isEditing = true
      }
    },

    // 미니홈피 정보 저장
    async saveMinihome() {
      this.isSaving = true

      try {
        // FormData로 파일과 데이터를 함께 전송
        const formData = new FormData()

        // 기본 사용자 정보 추가
        formData.append('userId', this.userInfo.userId)
        formData.append('todayMood', this.userInfo.todayMood || '')
        formData.append('statusMessage', this.userInfo.statusMessage || '')
        formData.append('birthDate', this.userInfo.birthDate || '')
        formData.append('gender', this.userInfo.gender || '')
        formData.append('region', this.userInfo.region || '')
        formData.append('hobby', this.userInfo.hobby || '')
        formData.append('youtubeVideoId', this.userInfo.youtubeVideoId || '')
        formData.append('backgroundColor', this.userInfo.backgroundColor)
        formData.append('appliedThemeId', this.userInfo.theme || '')

        // 프로필 이미지 파일이 있으면 추가
        if (this.profileImageFile) {
          formData.append('profileImageFile', this.profileImageFile)
        }

        // API 호출
        const response = await axios.post('http://localhost:8080/api/updateMiniHome', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        })

        if (response.data.success) {
          this.showMessage('미니홈피가 성공적으로 저장되었습니다!', 'success')
          this.isEditing = false

          // 서버에서 반환된 새로운 프로필 이미지 URL이 있으면 업데이트
          if (response.data.profileImageUrl) {
            this.userInfo.profileImage = response.data.profileImageUrl
          }

          // 파일 관련 임시 데이터 초기화
          this.profileImageFile = null
          this.previewImage = null

        } else {
          throw new Error(response.data.message || '저장에 실패했습니다.')
        }

      } catch (error) {
        console.error('미니홈피 저장 실패:', error)
        this.showMessage(
          error.response?.data?.message || '미니홈피 저장에 실패했습니다.',
          'error'
        )
        // 에러 발생 시 원본 데이터로 복원
        this.userInfo = { ...this.originalUserInfo }
      } finally {
        this.isSaving = false
      }
    },

    // 프로필 이미지 파일 변경
    onFileChange(e) {
      const file = e.target.files[0]
      if (file) {
        // 파일 크기 체크 (예: 5MB 제한)
        if (file.size > 5 * 1024 * 1024) {
          this.showMessage('파일 크기는 5MB 이하여야 합니다.', 'error')
          return
        }

        // 파일 형식 체크
        if (!file.type.startsWith('image/')) {
          this.showMessage('이미지 파일만 업로드 가능합니다.', 'error')
          return
        }

        this.profileImageFile = file
        this.previewImage = URL.createObjectURL(file)
      }
    },

    // 탭 변경
    changeTab(tabName) {
      this.activeTab = tabName
    },

    // 테마 이름 반환
    getThemeName(themeId) {
      const themes = {
        '1': '심플',
        '2': '귀여운',
        '3': '세련된',
        '4': '빈티지'
      }
      return themes[themeId] || '심플'
    },

    // 메시지 표시
    showMessage(message, type) {
      this.saveMessage = message
      this.saveMessageClass = type === 'success' ? 'alert-success' : 'alert-danger'

      // 3초 후 메시지 자동 숨김
      setTimeout(() => {
        this.saveMessage = ''
      }, 3000)
    },

    // 수정 취소
    cancelEdit() {
      this.userInfo = { ...this.originalUserInfo }
      this.isEditing = false
      this.previewImage = null
      this.profileImageFile = null
    }
  }
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

.alert {
  margin-bottom: 10px;
  padding: 8px 12px;
  font-size: 0.9rem;
}
</style>
