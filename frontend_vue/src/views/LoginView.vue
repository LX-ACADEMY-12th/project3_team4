<template>
  <div class="container">

    <header class="header">
      <div class="logo-wrapper">
        <img src="@/assets/images/lx.png" alt="LX 로고" class="logo-img">
      </div>
      <h1 class="title">XIIWORLD</h1>
      <p class="team">team4</p>
    </header>

    <div class="form-wrapper">
      <form @submit.prevent="handleLogin">
        <div class="input-container">
          <input type="text" placeholder="아이디(이메일)" v-model="loginData.loginId" class="input-field" required>
        </div>
        <div class="input-container">
          <input type="password" placeholder="비밀번호" v-model="loginData.loginPw" class="input-field" required>
        </div>

        <div class="button-row">
          <button type="submit" class="login-btn">로그인</button>

          <button type="button" @click="$router.push('/signup')" class="signup-btn">회원가입</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
// 필요한 Vue 라이브러리 및 외부 라이브러리를 가져옵니다.
import { ref } from 'vue' // 반응형 상태 관리를 위한 ref 함수
import { useRouter } from 'vue-router' // 라우터 객체 사용을 위한 훅
import axios from 'axios' // HTTP 통신을 위한 Axios 라이브러리

// 라우터 인스턴스를 가져와 router 변수에 할당.
const router = useRouter()

// ref()를 사용해 loginData 객체를 반응형으로 만듭니다.
// 이렇게 하면 loginData의 값이 변경될 때 템플릿도 자동으로 업데이트됩니다.
const loginData = ref({
  loginId: '',
  loginPw: ''
})

/**
 * 로그인 버튼 클릭 시 실행되는 비동기 함수.
 * 서버에 로그인 요청을 보냅니다.
 */
async function handleLogin() {
  try {
    // Axios를 사용하여 'http://localhost:8080/api/login' 엔드포인트에 POST 요청을 보냅니다.
    // loginData.value 객체 전체를 JSON 형태로 서버에 전송합니다.
    const response = await axios.post('http://localhost:8080/api/login', loginData.value)

    // 서버 응답의 상태 코드를 확인. 200은 성공을 의미합니다.
    if (response.status === 200 && response.data) {
      alert('로그인 성공')
      // 로그인 성공 시 사용자의 아이디를 세션 스토리지에 저장.
      // 이렇게 저장된 값은 다른 페이지에서 사용자의 로그인 상태를 확인하는 데 사용됩니다.
      const userData = response.data;
      sessionStorage.setItem('loginId', userData.loginId);
      sessionStorage.setItem('userId', userData.userId); // 실제 DB의 PK
      sessionStorage.setItem('nickname', userData.nickname);

      // 로그인 성공 후 'minihome' 경로로 페이지를 이동시킵니다.
      router.push(`/minihome/${userData.loginId}`)
    } else {
      // 200 OK가 아닌 다른 성공 응답(예: 204 No Content)이 올 경우 처리.
      alert('로그인 실패: 서버 응답 오류')
      console.error('서버 응답 오류:', response.status, response.data)
    }

  } catch (error) {
    // 요청 실패 시(네트워크 오류, 4xx, 5xx 에러 등) 이 블록이 실행됩니다.

    // axios 에러 객체의 response 속성을 확인하여 서버 응답이 있었는지 확인.
    if (error.response) {
      // 서버가 응답을 보냈지만, 상태 코드가 2xx 범위가 아닌 경우(예: 401 Unauthorized, 404 Not Found).
      console.error('로그인 에러:', error.response.data)
      // 서버에서 보낸 에러 메시지를 alert로 표시하거나, 기본 메시지를 보여줍니다.
      alert('로그인 실패: ' + (error.response.data || '아이디 또는 비밀번호가 일치하지 않습니다.'))
    } else if (error.request) {
      // 요청이 전송되었으나, 응답을 받지 못한 경우(네트워크 연결 문제, CORS 문제 등).
      console.error('네트워크 에러:', error.request)
      alert('로그인 실패: 네트워크 연결을 확인해주세요.')
    } else {
      // 요청을 보내기 전에 발생한 기타 오류(잘못된 요청 설정 등).
      console.error('기타 에러:', error.message)
      alert('로그인 실패: 알 수 없는 오류가 발생했습니다.')
    }
  }
}
</script>

<style scoped>
/*
'scoped'는 이 스타일이 현재 컴포넌트에만 적용되도록 함.
기존 .input-id, .input-pw를 .input-field로 통일하여 재사용성을 높임.
*/

.container {
  width: 400px;
  height: 400px;
  background-color: white;
  border-radius: 50%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 40px;
  box-sizing: border-box;
}

.header {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  gap: 15px;
}

.logo-wrapper {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: black;
  border: 2px solid #ddd;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0 auto 10px auto;
  overflow: hidden;
}

.logo-img {
  width: 40px;
  height: 40px;
  object-fit: contain;
}

.title {
  font-size: xx-large;
  font-weight: bold;
  color: gray;
}

.team {
  color: gray;
  font-size: smaller;
}

.form-wrapper {
  display: flex;
  flex-direction: column;
  gap: 10px;
  width: 300px;
  align-items: center;
}

.input-container {
  width: 100%;
  /* 부모 컨테이너에 맞게 너비를 100%로 설정. */
}

.input-field {
  width: 100%;
  /* input 필드 너비를 부모 컨테이너(input-container)에 맞춤. */
  box-sizing: border-box;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.button-row {
  display: flex;
  gap: 10px;
  width: 100%;
  justify-content: center;
}

.login-btn,
.signup-btn {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
}

.login-btn {
  background-color: #007bff;
  color: white;
}

.signup-btn {
  background-color: #6c757d;
  color: white;
}
</style>
