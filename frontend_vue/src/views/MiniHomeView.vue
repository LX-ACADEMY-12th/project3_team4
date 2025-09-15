<template>
  <div class="d-flex flex-column p-3" :style="{
    'font-family': 'DotGothic16, sans-serif',
    height: '700px',
    width: '900px',
    'background-color': userInfo.backgroundColor,
  }">

    <!-- 상단 헤더: 미니홈피 제목, 방문자 통계, 수정 버튼 영역 -->
    <div class="d-flex p-2 mb-3 text-black justify-content-between align-items-center">
      <!-- 왼쪽: 미니홈피 소유자 이름과 방문자 통계 -->
      <div class="d-flex flex-column ms-4 me-4 align-items-center border border-dark">
        <span class="m-3">{{ userInfo.nickname || '사용자' }}님의 미니홈피</span>
        <div class="border border-dark">
          <!-- TODAY: 오늘 방문자 수, TOTAL: 총 방문자 수 -->
          <span class="badge bg-danger me-1">TODAY {{ visitCount?.todayCount || '13' }}</span>
          <span class="badge bg-secondary">TOTAL {{ visitCount?.totalCount || '13' }}</span>
        </div>
      </div>

      <!-- 중앙: 싸이월드 로고 영역 -->
      <div class="border border-dark col-8 mx-auto">
        <img src="https://via.placeholder.com/100x30/007bff/ffffff?text=CYWORLD" alt="CYWORLD" />
        <img src="https://via.placeholder.com/100x30/007bff/ffffff?text=CYWORLD" />
      </div>

      <!-- 오른쪽: 수정 버튼 (본인 미니홈피일 때만 표시) -->
      <div v-if="isMyMinihome">
        <button class="btn btn-sm btn-outline-info" @click="toggleEdit" :disabled="isSaving">
          <!-- 수정 모드에 따라 버튼 텍스트 변경: 수정 → 저장 → 저장중... -->
          {{ isSaving ? '저장중...' : (isEditing ? '저장' : '수정') }}
        </button>
      </div>
    </div>

    <!-- 저장 성공/실패 메시지 알림 -->
    <div v-if="saveMessage" class="alert alert-dismissible fade show" :class="saveMessageClass" role="alert">
      {{ saveMessage }}
      <button type="button" class="btn-close" @click="saveMessage = ''"></button>
    </div>

    <!-- 메인 컨텐츠 영역: 왼쪽 사이드바 + 오른쪽 메인 콘텐츠 -->
    <div class="d-flex flex-fill flex-row">

      <!-- 왼쪽 사이드바: 프로필, 기분, 개인정보, 상태메시지 등 -->
      <div class="d-flex flex-column bg-white p-2 me-2 col-3 sidebar-left">

        <!-- 프로필 사진 영역 -->
        <div class="d-flex flex-column align-items-center mb-2 border border-dark profile-box">
          <div class="w-100 h-100">
            <!-- 수정 모드일 때: 파일 업로드 + 미리보기 -->
            <div v-if="isEditing">
              <input type="file" @change="onFileChange" class="form-control form-control-sm mb-1" accept="image/*" />
              <!-- 새로 선택한 이미지 미리보기 -->
              <img v-if="previewImage" :src="previewImage" class="profile-img" />
              <!-- 기존 프로필 이미지 표시 -->
              <img v-else-if="userInfo.profileImage" :src="userInfo.profileImage" class="profile-img" />
            </div>
            <!-- 일반 모드일 때: 프로필 이미지만 표시 -->
            <img v-else :src="userInfo.profileImage || 'https://via.placeholder.com/120x120/cccccc/ffffff?text=Profile'"
              class="profile-img" />
          </div>
        </div>

        <!-- 오늘의 기분 영역 -->
        <div class="text-center mb-1 small">
          <!-- 수정 모드: 기분 선택 드롭다운 -->
          <div v-if="isEditing">
            <select v-model="userInfo.todayMood" class="form-select form-select-sm">
              <option value="">[기분 선택]</option>
              <option value="😊 행복">😊 행복</option>
              <option value="😢 슬픔">😢 슬픔</option>
              <option value="😡 화남">😡 화남</option>
              <option value="😴 피곤">😴 피곤</option>
              <option value="😍 설렘">😍 설렘</option>
              <option value="🤔 고민중">🤔 고민중</option>
              <option value="😪 휴식중">😪 휴식중</option>
            </select>
          </div>
          <!-- 일반 모드: 현재 기분 표시 -->
          <div v-else class="text-muted">TODAY IS {{ userInfo.todayMood || '[기분]' }}</div>
        </div>

        <!-- 생일과 성별 영역 -->
        <div class="text-center mb-1 small">
          <!-- 수정 모드: 생일 날짜 선택 + 성별 선택 -->
          <div v-if="isEditing" class="d-flex gap-1">
            <input type="date" v-model="userInfo.birthDate" class="form-control form-control-sm" />
            <select v-model="userInfo.gender" class="form-select form-select-sm" style="max-width: 70px;">
              <option value="">성별</option>
              <option value="남자">남자</option>
              <option value="여자">여자</option>
            </select>
          </div>
          <!-- 일반 모드: 생일과 성별 정보 표시 -->
          <div v-else>
            생일: {{ userInfo.birthDate || '등록 안 됨' }}
            <span v-if="userInfo.gender"> / {{ userInfo.gender }}</span>
          </div>
        </div>

        <!-- 지역 정보 영역 -->
        <div class="text-center mb-1 small">
          <!-- 수정 모드: 지역 입력 필드 -->
          <div v-if="isEditing">
            <input type="text" v-model="userInfo.region" class="form-control form-control-sm" placeholder="지역 입력"
              maxlength="50" />
          </div>
          <!-- 일반 모드: 현재 지역 표시 -->
          <div v-else>지역: {{ userInfo.region || '등록 안 됨' }}</div>
        </div>

        <!-- 취미 정보 영역 -->
        <div class="text-center mb-2 small">
          <!-- 수정 모드: 취미 선택 드롭다운 -->
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
          <!-- 일반 모드: 현재 취미 표시 -->
          <div v-else>취미: {{ userInfo.hobby || '등록 안 됨' }}</div>
        </div>

        <!-- 배경색 선택 영역 -->
        <div class="text-center mb-2 small">
          <!-- 수정 모드: 배경색 선택 드롭다운 -->
          <div v-if="isEditing">
            <select v-model="userInfo.backgroundColor" class="form-select form-select-sm">
              <option value="#f8f9fa">기본 (연회색)</option>
              <option value="#cce5ff">파랑</option>
              <option value="#fddde6">분홍</option>
              <option value="#212529">검정</option>
            </select>
          </div>
          <!-- 일반 모드: 현재 배경색을 작은 박스로 표시 -->
          <div v-else>
            배경색:
            <span class="d-inline-block"
              :style="{ backgroundColor: userInfo.backgroundColor, width: '40px', height: '15px', border: '1px solid #000' }"></span>
          </div>
        </div>

        <!-- 테마 선택 영역 -->
        <div class="text-center mb-2 small">
          <!-- 수정 모드: 테마 선택 드롭다운 -->
          <div v-if="isEditing">
            <select v-model="userInfo.theme" class="form-select form-select-sm">
              <option value="1">심플(기본)</option>
              <option value="2">귀여운</option>
              <option value="3">세련된</option>
              <option value="4">빈티지</option>
            </select>
          </div>
          <!-- 일반 모드: 현재 테마명 표시 -->
          <div v-else>
            테마: {{ getThemeName(userInfo.theme) }}
          </div>
        </div>

        <!-- 상태 메시지 영역 -->
        <div class="d-flex flex-column align-items-center mb-2 border border-dark text-center status-box">
          <!-- 일반 모드: 상태 메시지 표시 -->
          <p v-if="!isEditing" class="small w-100 m-0 d-flex align-items-center justify-content-center h-100">
            {{ userInfo.statusMessage || '사용자 작성 멘트' }}
          </p>
          <!-- 수정 모드: 상태 메시지 입력 텍스트 영역 -->
          <textarea v-else v-model="userInfo.statusMessage" class="form-control form-control-sm h-100"
            placeholder="상태 메시지를 입력하세요" maxlength="200"></textarea>
        </div>

        <!-- 홈 주인 표시 영역 -->
        <div class="border border-dark">
          <div class="d-flex align-items-center">
            <span class="small me-1">홈주인</span>
            <span class="small">{{ userInfo.nickname || '나' }}</span>
          </div>
        </div>

        <!-- 친구 목록 (파도타기) 영역 -->
        <div>
          <select v-model="selectedFriend" class="form-select form-select-sm" @change="goToFriendMiniHome">
            <option disabled value="">[파도타기]</option>
            <!-- 전체 사용자 목록을 순회하여 친구 선택 옵션 생성 -->
            <option v-for="user in users" :key="user.userId" :value="user.loginId">
              {{ user.nickname }}
            </option>
          </select>
        </div>
      </div>

      <!-- 오른쪽 메인 콘텐츠 영역 -->
      <div class="d-flex flex-grow-1 col-9 flex-column border border-black">

        <!-- 상단 영역: 서비스 공간 + 유튜브 영상/음악 검색 영역 -->
        <div class="d-flex border border-dark h-25">
          <!-- 왼쪽: 빈 서비스 공간 -->
          <div class="d-flex col-8 justify-content-center align-items-center">
            <span class="w-100 text-center">{{ userInfo.emptySpaceText || '서비스 준비 중...' }}</span>
          </div>

          <!-- 오른쪽: 유튜브 영역 -->
          <!-- 수정 모드일 때: 음악 검색 인터페이스 표시 -->
          <div class="d-flex flex-grow-1 p-2" v-if="isEditing">
            <div class="w-100">
              <!-- 음악 검색 입력 폼 -->
              <div class="mb-2 music-search-container">
                <div class="input-group input-group-sm">
                  <!-- 검색어 입력 필드 (엔터키로도 검색 가능) -->
                  <input type="text" class="form-control" placeholder="음악 제목을 검색하세요..." v-model="musicSearchQuery"
                    @keyup.enter="searchMusic" />
                  <!-- 검색 버튼 (검색 중일 때 비활성화) -->
                  <button class="btn btn-outline-primary" type="button" @click="searchMusic" :disabled="isSearching">
                    {{ isSearching ? '검색중...' : '검색' }}
                  </button>
                </div>

                <!-- 음악 검색 결과 리스트 -->
                <div v-if="musicSearchResults.length > 0" class="music-search-results">
                  <div v-for="music in musicSearchResults" :key="music.videoId"
                    class="music-item p-2 border-bottom cursor-pointer"
                    :class="{ 'selected': selectedMusicId === music.videoId }" @click="selectMusic(music)">
                    <div class="d-flex align-items-center">
                      <!-- 음악 썸네일 이미지 -->
                      <img :src="music.thumbnail" class="music-thumbnail me-2" />
                      <div class="flex-grow-1">
                        <!-- 음악 제목 -->
                        <div class="music-title">{{ music.title }}</div>
                        <!-- 채널명 (아티스트명) -->
                        <div class="music-channel text-muted small">{{ music.channelTitle }}</div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- 현재 선택된 음악의 미리보기 -->
              <div v-if="selectedMusicId" class="mt-2">
                <small class="text-muted">선택된 음악:</small>
                <!-- 자동재생 없이 컨트롤만 있는 미리보기 iframe -->
                <iframe :src="`https://www.youtube.com/embed/${selectedMusicId}?autoplay=0&controls=1`" frameborder="0"
                  style="width: 100%; height: 80px"
                  allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                  allowfullscreen></iframe>
              </div>
            </div>
          </div>

          <!-- 일반 모드이고 유튜브 비디오 ID가 있을 때: 자동재생 영상 -->
          <div class="d-flex flex-grow-1 p-2" v-else-if="userInfo.youtubeVideoId">
            <!-- 자동재생, 반복재생, 플레이리스트 설정으로 BGM처럼 작동 -->
            <iframe
              :src="`https://www.youtube.com/embed/${userInfo.youtubeVideoId}?autoplay=1&loop=1&playlist=${userInfo.youtubeVideoId}`"
              frameborder="0" style="width: 100%; height: 100%"
              allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
              allowfullscreen></iframe>
          </div>

          <!-- 유튜브 비디오가 없을 때: 빈 공간 표시 -->
          <div class="d-flex flex-grow-1 p-2 justify-content-center align-items-center bg-light" v-else>
            <span class="text-muted">동영상 없음</span>
          </div>
        </div>

        <!-- 메인 콘텐츠 영역: 탭 시스템 (홈, 방명록, 사진첩) -->
        <div class="d-flex flex-grow-1 p-1 justify-content-center align-items-center border border-dark">
          <!-- 탭 콘텐츠 표시 영역 -->
          <div
            class="d-flex col-11 justify-content-center align-items-center h-100 border border-dark position-relative">

            <!-- 홈 탭: 미니룸 이미지 표시 -->
            <div v-if="activeTab === 'home'"
              class="position-absolute w-100 h-100 d-flex justify-content-center align-items-center">
              <img
                :src="userInfo.miniroomImage || 'https://img1.daumcdn.net/thumb/R720x0.q80/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F9938F0375BBEF5CC21'"
                style="width: 100%; height: 100%; object-fit: contain;" alt="미니룸" class="img-fluid" />
            </div>

            <!-- 방명록 탭: 방명록 컴포넌트 표시 -->
            <div v-else-if="activeTab === 'guestbook'" class="position-absolute w-100 h-100 d-flex p-3 overflow-auto">
              <!-- 미니홈피 주인의 로그인 ID를 props로 전달 -->
              <GuestbookView :mini-home-owner-login-id="miniHomeOwnerLoginId" />
            </div>

            <!-- 사진첩 탭: 사진 목록 표시 -->
            <div v-else-if="activeTab === 'photos'" class="position-absolute w-100 h-100 p-3 overflow-auto">
              <h6>사진첩</h6>
              <div class="row">
                <!-- 사진 목록을 순회하여 각 사진 표시 -->
                <div v-for="photo in photosList" :key="photo.id" class="col-4 mb-2">
                  <img :src="photo.url" :alt="photo.title" class="img-fluid border" @click="viewPhoto(photo)" />
                  <small class="d-block text-center">{{ photo.title }}</small>
                </div>
                <!-- 사진이 없을 때 메시지 표시 -->
                <div v-if="photosList.length === 0" class="text-center text-muted w-100">서비스 준비 중...</div>
              </div>
            </div>
          </div>

          <!-- 탭 네비게이션 영역 -->
          <div class="d-flex flex-column h-100 flex-grow-1 justify-content-start">
            <div class="d-flex flex-column flex-grow-1 justify-content-start">
              <ul class="nav nav-tabs flex-column">
                <!-- 홈 탭 -->
                <li class="nav-item border border-dark">
                  <a class="nav-link" :class="{ active: activeTab === 'home' }" href="#"
                    @click.prevent="activeTab = 'home'">홈</a>
                </li>
                <!-- 방명록 탭 -->
                <li class="nav-item border border-dark">
                  <a class="nav-link" :class="{ active: activeTab === 'guestbook' }" href="#"
                    @click.prevent="changeTab('guestbook')">방명록</a>
                </li>
                <!-- 사진첩 탭 -->
                <li class="nav-item border border-dark">
                  <a class="nav-link" :class="{ active: activeTab === 'photos' }" href="#"
                    @click.prevent="changeTab('photos')">사진첩</a>
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
      // === 편집 모드 관련 상태 ===
      isEditing: false,          // 현재 편집 모드인지 여부
      isSaving: false,           // 저장 중인지 여부 (버튼 비활성화용)
      previewImage: null,        // 새로 선택한 프로필 이미지 미리보기 URL
      profileImageFile: null,    // 업로드할 프로필 이미지 파일 객체
      saveMessage: '',           // 저장 성공/실패 메시지
      saveMessageClass: '',      // 메시지 스타일 클래스 (success/error)

      // === 사용자 식별 관련 ===
      miniHomeOwnerLoginId: null,  // 현재 보고 있는 미니홈피 소유자의 로그인 ID
      currentLoginId: null,        // 현재 로그인한 사용자의 로그인 ID

      // === 미니홈피 사용자 정보 ===
      userInfo: {
        userId: null,              // 사용자 고유 ID (primary key)
        nickname: null,            // 닉네임
        profileImage: null,        // 프로필 이미지 URL
        todayMood: null,          // 오늘의 기분
        statusMessage: null,       // 상태 메시지
        birthDate: null,          // 생년월일
        gender: null,             // 성별
        region: null,             // 지역
        hobby: null,              // 취미
        youtubeVideoId: null,     // 설정된 유튜브 비디오 ID (BGM)
        backgroundColor: '#f8f9fa', // 미니홈피 배경색
        theme: null,              // 적용된 테마 ID
      },

      // === 음악 검색 관련 ===
      musicSearchQuery: '',       // 음악 검색어
      musicSearchResults: [],     // 검색 결과 목록
      selectedMusicId: null,      // 현재 선택된 음악의 비디오 ID
      isSearching: false,         // 음악 검색 중인지 여부

      // === 기타 데이터 ===
      users: [],                  // 전체 사용자 목록 (파도타기용)
      selectedFriend: '',         // 선택된 친구의 로그인 ID
      originalUserInfo: {},       // 수정 취소를 위한 원본 데이터 백업
      visitCount: { todayCount: 0, totalCount: 0 }, // 방문자 통계
      loginUserPk: 3,             // 임시 로그인 사용자 PK
      friendsList: [],            // 친구 목록
      guestbookList: [],          // 방명록 목록
      photosList: [],             // 사진 목록
      activeTab: 'home',          // 현재 활성화된 탭
    }
  },

  watch: {
    // 라우트 변경 감지: 다른 사용자의 미니홈피로 이동할 때
    '$route'(to, fromRoute) {
      console.log('라우트 변경됨:', to.params.loginId);

      // 새로운 사용자 ID로 데이터 다시 로드
      if (to.params.loginId) {
        // 먼저 미니홈피 소유자 ID 설정
        this.miniHomeOwnerLoginId = to.params.loginId;

        // 그 다음 데이터 로드
        this.fetchMinihome(this.miniHomeOwnerLoginId);

        console.log('currentLoginId 설정:', this.currentLoginId);
        console.log('miniHomeOwnerLoginId 설정:', this.miniHomeOwnerLoginId);
        console.log('내 미니홈피인가?', this.isMyMinihome);
      }
    }
  },

  // 컴포넌트가 마운트된 후 실행되는 초기화 로직
  mounted() {
    // 1. 세션 스토리지에서 현재 로그인한 사용자의 ID를 가져옴
    this.currentLoginId = sessionStorage.getItem('loginId');

    // 2. URL 파라미터에서 방문한 미니홈피 소유자의 ID를 가져옴
    const miniHomeOwnerLoginId = this.$route.params.loginId;
    this.miniHomeOwnerLoginId = miniHomeOwnerLoginId;

    // 3. 전체 사용자 목록을 가져와 파도타기 기능 준비
    this.getUsers();

    // 4. 미니홈피 소유자 ID가 있으면 해당 미니홈피 데이터 로드
    if (miniHomeOwnerLoginId) {
      this.fetchMinihome(miniHomeOwnerLoginId);
    } else {
      // ID가 없으면 로그인 페이지로 리다이렉트
      console.error('가져올 사용자가 없습니다.');
      this.$router.push('/login');
    }
  },

  // 의존되는 데이터 변화에 따라 자동으로 값을 갱신
  computed: {
    /**
     * 현재 보고 있는 미니홈피가 내 것인지 판단
     * @returns {boolean} 내 미니홈피면 true, 아니면 false
     */
    isMyMinihome() {
      return this.miniHomeOwnerLoginId === this.currentLoginId;
    }
  },

  methods: {
    /**
     * 미니홈피 정보를 서버에서 가져오는 함수
     * @param {string} miniHomeOwnerLoginId 미니홈피 소유자의 로그인 ID
     */
    async fetchMinihome(miniHomeOwnerLoginId) {
      try {
        // 서버에 미니홈피 정보 요청
        const { data } = await axios.get('http://localhost:8080/api/showMiniHome', {
          params: { miniHomeOwnerLoginId },
        });

        // 받아온 데이터로 userInfo 객체 업데이트
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
          theme: data.appliedThemeId || null,
        };

        // 방문자 통계 정보 설정
        this.visitCount = data.visitCount
          ? data.visitCount
          : { todayCount: data.todayCount || 0, totalCount: data.totalCount || 0 };

      } catch (error) {
        console.error('미니홈피 정보를 가져오는데 실패했습니다.', error);
        this.showMessage('미니홈피 정보를 불러오는데 실패했습니다.', 'error');
      }
    },

    /**
     * 편집 모드 토글 및 저장 기능
     * 편집 모드가 아니면 편집 모드로 전환, 편집 모드면 저장 실행
     */
    async toggleEdit() {
      if (this.isEditing) {
        // 현재 편집 모드인 경우: 저장 로직 실행
        this.saveMinihome();
      } else {
        // 현재 편집 모드가 아닌 경우: 편집 모드로 전환

        // 1. 본인의 미니홈피인지 확인 (다른 사람 미니홈피는 수정 불가)
        if (!this.isMyMinihome) {
          this.showMessage('본인의 미니홈피만 수정할 수 있습니다.', 'error');
          return;
        }

        // 2. 편집 모드 활성화
        this.isEditing = true;

        // 3. 수정 취소를 위해 현재 데이터를 백업
        this.originalUserInfo = { ...this.userInfo };

        // 4. 음악 검색 관련 초기화
        this.selectedMusicId = this.userInfo.youtubeVideoId; // 현재 설정된 음악을 선택된 상태로
        this.musicSearchResults = []; // 검색 결과 초기화
        this.musicSearchQuery = '';   // 검색어 초기화
      }
    },

    /**
     * 미니홈피 정보를 서버에 저장하는 함수
     * FormData를 사용하여 파일과 일반 데이터를 함께 전송
     */
    async saveMinihome() {
      this.isSaving = true; // 저장 중 상태로 변경 (버튼 비활성화)

      try {
        // FormData 객체 생성 (파일 업로드를 위해 필요)
        const formData = new FormData();

        // 기본 사용자 정보를 FormData에 추가
        formData.append('userId', this.userInfo.userId); // 이거 user PK 값임.
        formData.append('miniHomeOwnerLoginId', this.miniHomeOwnerLoginId);
        formData.append('todayMood', this.userInfo.todayMood || '');
        formData.append('statusMessage', this.userInfo.statusMessage || '');
        formData.append('birthDate', this.userInfo.birthDate || '');
        formData.append('gender', this.userInfo.gender || '');
        formData.append('region', this.userInfo.region || '');
        formData.append('hobby', this.userInfo.hobby || '');

        // 중요: 선택된 음악 ID를 저장 (selectedMusicId 사용)
        formData.append('youtubeVideoId', this.selectedMusicId || '');

        formData.append('backgroundColor', this.userInfo.backgroundColor);
        formData.append('appliedThemeId', this.userInfo.theme || '');

        // 새로 선택한 프로필 이미지 파일이 있으면 추가
        if (this.profileImageFile) {
          formData.append('profileImageFile', this.profileImageFile);
        }

        // FormData의 내용을 콘솔에 출력
        console.log("서버로 전송할 FormData 내용:");
        for (let pair of formData.entries()) {
          console.log(pair[0] + ': ' + pair[1]);
        }

        // 서버에 저장 요청
        const response = await axios.post('http://localhost:8080/api/updateMiniHome', formData, {
          headers: {
            'Content-Type': 'multipart/form-data' // 파일 업로드를 위한 헤더
          }
        });

        // 저장 성공 처리
        if (response.data.success) {
          this.showMessage('미니홈피가 성공적으로 저장되었습니다!', 'success');
          this.isEditing = false; // 편집 모드 종료

          // 유튜브 비디오 ID 업데이트 (실제 저장된 값으로)
          this.userInfo.youtubeVideoId = this.selectedMusicId;

          // 서버에서 반환된 새로운 프로필 이미지 URL이 있으면 업데이트
          if (response.data.profileImageUrl) {
            this.userInfo.profileImage = response.data.profileImageUrl;
          }

          // 임시 데이터 초기화
          this.profileImageFile = null;
          this.previewImage = null;
          this.musicSearchResults = [];
          this.musicSearchQuery = '';

        } else {
          // 서버에서 실패 응답이 온 경우
          throw new Error(response.data.message || '저장에 실패했습니다.');
        }

      } catch (error) {
        console.error('미니홈피 저장 실패:', error);

        // 에러 메시지 표시
        this.showMessage(
          error.response?.data?.message || '미니홈피 저장에 실패했습니다.',
          'error'
        );

        // 에러 발생 시 원본 데이터로 복원
        this.userInfo = { ...this.originalUserInfo };
        this.selectedMusicId = this.userInfo.youtubeVideoId;

      } finally {
        this.isSaving = false; // 저장 완료 후 버튼 다시 활성화
      }
    },

    /**
     * 유튜브에서 음악을 검색하는 함수
     */
    async searchMusic() {
      // 검색어가 비어있으면 경고 메시지 표시
      if (!this.musicSearchQuery.trim()) {
        this.showMessage('검색어를 입력해주세요.', 'error');
        return;
      }

      this.isSearching = true; // 검색 중 상태로 변경

      try {
        // 서버의 음악 검색 API 호출
        const response = await axios.get('http://localhost:8080/api/music/search', {
          params: {
            query: this.musicSearchQuery,
            maxResults: 10 // 최대 10개 결과 요청
          }
        });

        // 검색 성공 처리
        if (response.data.success) {
          this.musicSearchResults = response.data.items;

          // 검색 결과가 없으면 메시지 표시
          if (this.musicSearchResults.length === 0) {
            this.showMessage('검색 결과가 없습니다. 다른 검색어를 시도해보세요.', 'error');
          }
        } else {
          this.showMessage('음악 검색에 실패했습니다.', 'error');
        }

      } catch (error) {
        console.error('음악 검색 오류:', error);
        this.showMessage('음악 검색 중 오류가 발생했습니다.', 'error');

      } finally {
        this.isSearching = false; // 검색 완료 후 상태 복원
      }
    },

    /**
     * 검색 결과에서 음악을 선택하는 함수
     * @param {Object} music 선택된 음악 객체 (videoId, title, channelTitle 등 포함)
     */
    selectMusic(music) {
      this.selectedMusicId = music.videoId;
      console.log('선택된 음악:', music.title, '(ID:', music.videoId, ')');

      // 선택 후 검색 결과를 숨기기
      this.musicSearchResults = [];
    },

    /**
     * 프로필 이미지 파일 변경 처리 함수
     * @param {Event} e 파일 입력 이벤트
     */
    onFileChange(e) {
      const file = e.target.files[0];

      if (file) {
        // 파일 크기 검증 (5MB 제한)
        if (file.size > 5 * 1024 * 1024) {
          this.showMessage('파일 크기는 5MB 이하여야 합니다.', 'error');
          return;
        }

        // 파일 타입 검증 (이미지 파일만 허용)
        if (!file.type.startsWith('image/')) {
          this.showMessage('이미지 파일만 업로드 가능합니다.', 'error');
          return;
        }

        // 파일을 변수에 저장하고 미리보기 URL 생성
        this.profileImageFile = file;
        this.previewImage = URL.createObjectURL(file);
      }
    },

    /**
     * 탭 변경 함수
     * @param {string} tabName 변경할 탭 이름 ('home', 'guestbook', 'photos')
     */
    changeTab(tabName) {
      this.activeTab = tabName;

      // 방명록 탭으로 변경할 때 추가 로직 (필요시)
      if (tabName === "guestbook" && this.miniHomeOwnerLoginId) {
        // GuestbookView 컴포넌트가 props 변경을 감지하도록 구현되어 있음
      }
    },

    /**
     * 테마 ID를 테마 이름으로 변환하는 함수
     * @param {string} themeId 테마 ID
     * @returns {string} 테마 이름
     */
    getThemeName(themeId) {
      const themes = {
        '1': '심플',
        '2': '귀여운',
        '3': '세련된',
        '4': '빈티지'
      };
      return themes[themeId] || '심플';
    },

    /**
     * 성공/에러 메시지를 화면에 표시하는 함수
     * @param {string} message 표시할 메시지
     * @param {string} type 메시지 타입 ('success' 또는 'error')
     */
    showMessage(message, type) {
      this.saveMessage = message;
      this.saveMessageClass = type === 'success' ? 'alert-success' : 'alert-danger';

      // 3초 후 메시지 자동 숨김
      setTimeout(() => {
        this.saveMessage = '';
      }, 3000);
    },

    /**
     * 편집 취소 함수 (현재는 사용되지 않지만 필요시 활용 가능)
     */
    cancelEdit() {
      // 원본 데이터로 복원
      this.userInfo = { ...this.originalUserInfo };

      // 편집 모드 종료
      this.isEditing = false;

      // 임시 데이터 초기화
      this.previewImage = null;
      this.profileImageFile = null;
      this.musicSearchResults = [];
      this.musicSearchQuery = '';
      this.selectedMusicId = this.userInfo.youtubeVideoId;
    },

    /**
     * 전체 사용자 목록을 서버에서 가져오는 함수 (파도타기 기능용)
     */
    async getUsers() {
      try {
        const response = await axios.get('http://localhost:8080/api/friend/users');
        this.users = response.data;

      } catch (err) {
        console.error(`사용자 목록 가져오기 실패: ${err}`);
      }
    },

    /**
     * 선택된 친구의 미니홈피로 이동하는 함수 (파도타기)
     */
    goToFriendMiniHome() {
      if (this.selectedFriend) {
        // Vue Router를 사용하여 친구의 미니홈피로 라우팅
        this.$router.push({
          name: 'minihome',
          params: { loginId: this.selectedFriend }
        });
      }
    },

    /**
     * 사진 클릭 시 상세보기 함수 (현재는 빈 함수, 필요시 구현)
     * @param {Object} photo 클릭된 사진 객체
     */
    viewPhoto(photo) {
      // 사진 상세보기 로직 구현 예정
      console.log('사진 클릭:', photo);
    }
  }
}
</script>

<style scoped>
/* 구글 폰트 DotGothic16 import */
@import url('https://fonts.googleapis.com/css2?family=DotGothic16&display=swap');

/* === 네비게이션 탭 스타일 === */
.nav-tabs .nav-link {
  border-radius: 0;
  font-size: 0.875rem;
}

.nav-tabs .nav-link.active {
  background-color: #007bff;
  color: white;
  border-color: #007bff;
}

/* === 배지 스타일 === */
.badge {
  font-size: 0.75rem;
}

/* === 왼쪽 사이드바 스타일 === */
.sidebar-left {
  font-size: 0.8rem;
  line-height: 1.2;
}

/* === 프로필 영역 스타일 === */
.profile-box {
  width: 100%;
  height: 150px;
  margin-bottom: 8px;
  overflow: hidden;
  /* 이미지가 박스를 넘지 않도록 */
}

.profile-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  /* 이미지 비율 유지하면서 박스에 맞춤 */
}

/* === 상태 메시지 박스 스타일 === */
.status-box {
  width: 100%;
  height: 60px;
}

/* === 폼 컨트롤 스타일 === */
.form-control-sm,
.form-select-sm,
textarea.form-control-sm {
  font-size: 0.8rem;
  padding: 2px 6px;
}

textarea.form-control-sm {
  resize: none;
  /* 텍스트영역 크기 조절 비활성화 */
}

/* === 알림 메시지 스타일 === */
.alert {
  margin-bottom: 10px;
  padding: 8px 12px;
  font-size: 0.9rem;
}

/* 검색 입력 필드의 부모 컨테이너에 relative position 추가 */
.music-search-container {
  position: relative;
  /* absolute인 검색 결과의 기준점 설정 */
}

/* === 음악 검색 관련 스타일 === */
.music-search-results {
  max-height: 200px;
  overflow-y: auto;
  border: 1px solid #dee2e6;
  border-radius: 4px;
  background-color: white;
  position: absolute;
  /* absolute로 변경하여 다른 요소 위에 표시 */
  z-index: 9999;
  /* 매우 높은 z-index로 설정 */
  top: 100%;
  /* 검색 입력 필드 바로 아래에 표시 */
  left: 0;
  right: 0;
  /* 부모 요소의 너비에 맞춤 */
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  /* 더 진한 그림자로 강조 */
}

.music-item {
  cursor: pointer;
  transition: background-color 0.2s ease;
  /* 호버 효과를 위한 전환 */
}

.music-item:hover {
  background-color: #f8f9fa;
  /* 마우스 오버 시 배경색 변경 */
}

.music-item.selected {
  background-color: #e3f2fd;
  /* 선택된 아이템 배경색 */
  border-left: 3px solid #2196f3;
  /* 선택된 아이템 왼쪽 테두리 */
}

.music-thumbnail {
  width: 60px;
  height: 45px;
  object-fit: cover;
  /* 썸네일 비율 유지 */
  border-radius: 4px;
}

.music-title {
  font-size: 0.85rem;
  font-weight: 500;
  line-height: 1.3;
  margin-bottom: 2px;
  overflow: hidden;
  /* 긴 제목 처리 */
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  /* 최대 2줄로 제한 */
  -webkit-box-orient: vertical;
}

.music-channel {
  font-size: 0.75rem;
}

/* === 유틸리티 클래스 === */
.cursor-pointer {
  cursor: pointer;
}
</style>
