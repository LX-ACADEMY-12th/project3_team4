<template>
  <div class="d-flex flex-column p-3"
    :style="{ 'font-family': 'DotGothic16, sans-serif', 'height': '700px', 'width': '900px', 'background-color': userInfo.backgroundColor }">
    <div class=" d-flex p-2 mb-3 text-black justify-content-between align-items-center">
      <div class="d-flex flex-column ms-4 me-4 align-items-center border border-dark">
        <span class="m-3">{{ userInfo.nickname || '사용자' }}님의 미니홈피</span>
        <div class="border border-dark">
          <span class="badge bg-danger me-1">TODAY {{ visitCount?.todayCount || '13' }}</span>
          <span class="badge bg-secondary">TOTAL {{ visitCount?.totalCount || '13' }}</span>
        </div>
      </div>

      <div class="border border-dark col-8 mx-auto">
        <img src="https://via.placeholder.com/100x30/007bff/ffffff?text=CYWORLD" alt="CYWORLD">
      </div>

      <div>
        <button class="btn btn-sm btn-outline-info">
          수정
        </button>
      </div>
    </div>

    <div class="d-flex flex-fill flex-row">
      <div class="d-flex flex-column bg-white p-3 me-3">
        <div class="d-flex flex-column align-items-center h-75 mb-3 border border-dark">
          <div class="mt-1 mb-1 h-75 w-100">
            <img :src="userInfo.profileImage || 'https://via.placeholder.com/120x120/cccccc/ffffff?text=Profile'"
              class="border p-1 img-fluid" alt="프로필 사진" style="width: 100%; height: 100%; object-fit: cover;">
          </div>

          <div class="flex-grow-1 align-items-center w-100 p-1">
            <small class="text-muted">TODAY IS {{ userInfo.todayMood || '[기분]' }}</small>
          </div>
        </div>

        <div class="d-flex flex-column align-items-center mb-3 border border-dark text-center">
          <p class="small w-100">{{ userInfo.statusMessage || '사용자 작성 멘트' }}</p>
        </div>

        <div class="border border-dark">
          <div class="d-flex align-items-center">
            <span class="small me-1">홈주인</span>
            <span class="small">{{ userInfo.nickname || '나' }}</span>
          </div>

          <div class="dropdown">
            <button
              class="btn btn-outline-secondary btn-sm dropdown-toggle w-100 d-flex justify-content-between align-items-center"
              type="button" data-bs-toggle="dropdown">
              <span>★ 일촌 파도타기</span>
            </button>
            <ul class="dropdown-menu w-100">
              <li v-for="friend in friendsList" :key="friend.id">
                <a class="dropdown-item small" href="#" @click="visitFriend(friend.id)">
                  {{ friend.nickname }}
                </a>
              </li>
              <li v-if="friendsList.length === 0">
                <span class="dropdown-item small text-muted">일촌이 없습니다</span>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <div class="d-flex flex-grow-1 flex-column col-8 border border-black">
        <div class="d-flex border border-dark h-25">
          <div class="d-flex col-8 justify-content-center align-items-center">
            <span class="w-100 text-center">{{ userInfo.emptySpaceText || '빈공간' }}</span>
          </div>

          <div class="d-flex flex-grow-1 p-2" v-if="userInfo.youtubeVideoId">
            <iframe :src="`https://www.youtube.com/embed/${userInfo.youtubeVideoId}`" frameborder="0"
              style="width: 100%; height: 100%;"
              allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
              allowfullscreen>
            </iframe>
          </div>
          <div class="d-flex flex-grow-1 p-2 justify-content-center align-items-center bg-light" v-else>
            <span class="text-muted">동영상 없음</span>
          </div>
        </div>

        <div class="d-flex flex-grow-1 p-1 justify-content-center align-items-center border border-dark">
          <div class="d-flex col-10 justify-content-center align-items-center h-100 border border-dark">
            <div v-if="activeTab === 'home'" class="w-100 h-100 d-flex justify-content-center align-items-center">
              <img :src="userInfo.miniroomImage || 'https://img1.daumcdn.net/thumb/R720x0.q80/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F9938F0375BBEF5CC21'"
                alt="미니룸" class="img-fluid">
            </div>

            <div v-else-if="activeTab === 'guestbook'" class="w-100 h-100 p-3 overflow-auto">
              <GuestbookView />
              <!-- 여기서부터 가라 
              <div class="mb-3">
                <h6>방명록</h6>
                <button class="btn btn-primary btn-sm" @click="showGuestbookForm = !showGuestbookForm">
                  글쓰기
                </button>
              </div>

              <div v-if="showGuestbookForm" class="border p-2 mb-3 bg-light">
                <div class="mb-2">
                  <input v-model="newGuestbook.author" placeholder="작성자" class="form-control form-control-sm mb-1">
                  <textarea v-model="newGuestbook.content" placeholder="내용을 입력하세요" class="form-control form-control-sm"
                    rows="3"></textarea>
                </div>
                <button @click="addGuestbook" class="btn btn-success btn-sm me-1">등록</button>
                <button @click="showGuestbookForm = false" class="btn btn-secondary btn-sm">취소</button>
              </div>

              <div v-for="entry in guestbookList" :key="entry.id" class="border-bottom py-2">
                <div class="d-flex justify-content-between">
                  <strong class="small">{{ entry.author }}</strong>
                  <small class="text-muted">{{ formatDate(entry.createdAt) }}</small>
                </div>
                <p class="small mb-1">{{ entry.content }}</p>
              </div>
              
              여기까지 -->

              <div v-if="guestbookList.length === 0" class="text-center text-muted">
                첫 방명록을 남겨주세요!
              </div>
            </div>

            <div v-else-if="activeTab === 'photos'" class="w-100 h-100 p-3 overflow-auto">
              <h6>사진첩</h6>
              <div class="row">
                <div v-for="photo in photosList" :key="photo.id" class="col-4 mb-2">
                  <img :src="photo.url" :alt="photo.title" class="img-fluid border" @click="viewPhoto(photo)">
                  <small class="d-block text-center">{{ photo.title }}</small>
                </div>
                <div v-if="photosList.length === 0" class="text-center text-muted w-100">
                  사진이 없습니다
                </div>
              </div>
            </div>
          </div>

          <div class="d-flex flex-column h-100 flex-grow-1 justify-content-start">
            <div class="d-flex flex-column flex-grow-1 justify-content-start">
              <ul class="nav nav-tabs flex-column">
                <li class="nav-item border border-dark">
                  <a class="nav-link" :class="{ active: activeTab === 'home' }" href="#"
                    @click.prevent="activeTab = 'home'">홈</a>
                </li>
                <li class="nav-item border border-dark">
                  <a class="nav-link" :class="{ active: activeTab === 'guestbook' }" href="#"
                    @click.prevent="changeTab('guestbook')">방명록</a>
                </li>
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

import axios from 'axios';
import GuestbookView from './GuestbookView.vue'


export default {
  components: { GuestbookView },


  // 컴포넌트의 이름
  name: 'MiniHomepage',

  // 모든 데이터를 'data' 안에 직접 작성합니다.
  data() {
    return {
      // API 호출 전 초기 상태는 null 또는 빈 객체로 설정
      userInfo: {
        userId: null,
        nickname: null,
        profileImagePath: null,
        todayMood: null,
        statusMessage: null,
        youtubeVideoId: null,
        backgroundColor: '#eeeeee',
      },
      visitCount: {
        todayCount: 0,
        totalCount: 0
      },
      logInUserId: "leesuji", // 로그인한 사용자 ID (임시값) <- 세션 정보로 불러와야 함.

      // 친구 목록: 배열로 여러 개의 객체를 넣습니다.
      friendsList: [
        { id: 1, nickname: '김일촌' },
        { id: 2, nickname: '박일촌' },
        { id: 3, nickname: '최일촌' },
      ],
      // 방명록 목록 -- 가라여서 지울거임
      guestbookList: [
        { id: 1, author: '방문자', content: '미니홈피 정말 멋져요!', createdAt: new Date() },
        { id: 2, author: '개발자', content: '추억의 싸이월드 감성!', createdAt: new Date() },
      ],
      // 사진첩 목록
      photosList: [
        { id: 1, url: 'https://placehold.co/150/ff0000/ffffff?text=사진1', title: '여행사진' },
        { id: 2, url: 'https://placehold.co/150/00ff00/ffffff?text=사진2', title: '강아지' },
        { id: 3, url: 'https://placehold.co/150/0000ff/ffffff?text=사진3', title: '하늘' },
      ],
      // UI 상태 관리 변수
      activeTab: 'home',
      showGuestbookForm: false,
      newGuestbook: {
        author: '',
        content: '',
      },
    };
  },

  // 라이프사이클 훅으로, 컴포넌트의 인스턴스가 DOM에 완전 연결되고 렌더링 된 후 호출
  mounted() {
    // 사용자 정보를 가져오는 메서드실행
    this.fetchUserInfo(this.logInUserId); // 로그인 사용자 ID로 정보 조회
  },

  // 컴포넌트가 사용할 메서드(함수)
  methods: {

    // user 정보 가져오기
    async fetchUserInfo(logInUserId) {
      try {
        const response = await axios.get(`http://localhost:8080/api/my-minihome?loginUserId=${logInUserId}`);

        // API 응답 객체에서 userInfo와 visitCount를 각각 할당
        this.userInfo = response.data;
        this.visitCount = response.data.visitCount;
        this.userInfo.backgroundColor = '#f8f9fa'; // 삭제해도돼 검정색배경이라 안보여서 추가함

      } catch (error) {
        // API 요청 실패 시 에러를 콘솔에 출력
        console.error("사용자 정보를 가져오는데 실패했습니다.", error);

        // 에러 발생 시 가짜 데이터 할당
        this.userInfo = {
          nickname: '에러',
          profileImage: 'https://via.placeholder.com/120x120/cccccc/ffffff?text=Error',
          todayMood: '[에러]',
          statusMessage: '데이터를 불러오지 못했습니다.',
          emptySpaceText: '정보 로딩 실패',
          youtubeVideoId: null,
          backgroundColor: '#FFFFFF' // 에러 시 기본 배경색
        };
        this.visitCount = {
          today: 0,
          total: 0
        };
      }
    },
    changeTab(tabName) {
      this.activeTab = tabName;
    },

    // 🔥 여기 추가
    formatDate(date) {
      if (!date) return '';
      const d = new Date(date);
      const yyyy = d.getFullYear();
      const mm = String(d.getMonth() + 1).padStart(2, '0');
      const dd = String(d.getDate()).padStart(2, '0');
      const hh = String(d.getHours()).padStart(2, '0');
      const min = String(d.getMinutes()).padStart(2, '0');
      return `${yyyy}-${mm}-${dd} ${hh}:${min}`;
    }
  }
};
</script>


<style scoped>
@import url('https://fonts.googleapis.com/css2?family=DotGothic16&display=swap');

.bg-text-black {
  background-color: black;
  color: white;
}

.nav-tabs .nav-link {
  border-radius: 0;
  font-size: 0.875rem;
}

.nav-tabs .nav-link.active {
  background-color: #007bff;
  color: white;
  border-color: #007bff;
}

.dropdown-menu {
  font-size: 0.875rem;
}

.badge {
  font-size: 0.75rem;
}

.guestbook-entry {
  border-bottom: 1px solid #eee;
  padding: 10px 0;
}

.photo-thumbnail {
  cursor: pointer;
  transition: transform 0.2s;
}

.photo-thumbnail:hover {
  transform: scale(1.05);
}
</style>
