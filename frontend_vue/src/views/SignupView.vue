<template>
  <div class="signup-container">

    <header class="header">
      <div class="logo-wrapper">
        <img src="@/assets/images/lx.png" alt="LX 로고" class="logo-img">
      </div>
      <h1 class="title">XIIWORLD</h1>
      <p class="team">team4</p>
    </header>

    <h2 class="form-title">회원가입</h2>

    <div class="form-wrapper">
      <form @submit.prevent="signup">
        <div class="input-row">
          <input type="text" placeholder="아이디" v-model="loginInfo.loginId" @input="onLoginIdChange" class="input-field"
            required>
          <button type="button" @click="checkDuplicate" :class="['check-btn', {
            'btn-available': isIdAvailable,
            'btn-duplicate': isDuplicateChecked && !isIdAvailable
          }]" :disabled="!loginInfo.loginId">
            {{ buttonText }}
          </button>
        </div>

        <div class="input-row">
          <input type="text" placeholder="닉네임" v-model="loginInfo.nickname" class="input-field" required>
        </div>

        <div class="input-row">
          <input type="password" placeholder="비밀번호" v-model="loginInfo.loginPw" class="input-field" required>
        </div>

        <div class="input-row">
          <input type="password" placeholder="비밀번호 확인" v-model="loginInfo.loginPwConfirm" class="input-field" required>
        </div>

        <div class="button-row">
          <button type="submit" class="signup-btn">가입</button>
          <button type="button" @click="$router.push('/')" class="login-redirect-btn">로그인</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
// 필요한 Vue 라이브러리 및 외부 라이브러리를 가져옵니다.
import { ref, computed } from 'vue' // 'ref'는 반응형 상태를, 'computed'는 계산된 속성을 만듭니다.
import { useRouter } from 'vue-router' // 라우터 인스턴스를 가져와 페이지 이동에 사용합니다.
import axios from 'axios' // 서버와 통신하기 위한 HTTP 클라이언트 라이브러리입니다.

// 라우터 인스턴스를 가져와 `router` 변수에 할당합니다.
const router = useRouter()

// ref()를 사용해 폼 데이터를 반응형 객체로 만듭니다.
// 이 객체의 속성들이 변경되면, 템플릿의 입력 필드와 동기화됩니다.
const loginInfo = ref({
  loginId: '',          // 사용자가 입력할 아이디
  nickname: '',         // 사용자가 입력할 닉네임
  loginPw: '',          // 사용자가 입력할 비밀번호
  loginPwConfirm: ''    // 비밀번호 확인용 입력값
})

// 중복확인 상태를 관리하는 반응형 변수들.
// 이 변수들을 통해 UI(버튼 색상, 텍스트 등)를 동적으로 제어합니다.
const isDuplicateChecked = ref(false) // 중복확인 버튼을 클릭했는지 여부 (클릭 시 true로 변경)
const isIdAvailable = ref(false)      // 중복확인 결과 아이디가 사용 가능한지 여부

// computed 속성을 사용하여 중복확인 버튼의 텍스트를 동적으로 결정합니다.
// 의존하는 반응형 변수(isDuplicateChecked, isIdAvailable)가 변경되면 자동으로 재계산됩니다.
const buttonText = computed(() => {
  // 중복확인을 하지 않은 상태이면 '중복확인' 텍스트를 보여줍니다.
  if (!isDuplicateChecked.value) return '중복확인'
  // 중복확인 후 아이디가 사용 가능하면 '확인완료', 아니면 '중복' 텍스트를 보여줍니다.
  return isIdAvailable.value ? '확인완료' : '중복'
})

/**
 * 아이디 중복을 체크하는 비동기 함수입니다.
 * 사용자가 입력한 아이디를 서버에 보내 사용 가능 여부를 확인합니다.
 */
const checkDuplicate = async () => {
  // 사용자가 아이디를 입력했는지 유효성 검사. .trim()으로 공백을 제거합니다.
  if (!loginInfo.value.loginId.trim()) {
    alert('아이디를 입력해주세요.')
    return // 함수 실행을 중단합니다.
  }
  try {
    // Axios를 사용해 백엔드의 'checkDuplicate' API에 POST 요청을 보냅니다.
    const response = await axios.post('http://localhost:8080/api/checkDuplicate', { loginId: loginInfo.value.loginId })

    // 서버 응답이 'available'이면 true, 아니면 false를 반환하는 변수.
    const result = response.data === 'available'
    // 사용자에게 결과를 알림창으로 보여줍니다.
    alert(result ? '사용 가능한 아이디입니다.' : '이미 사용 중인 아이디입니다.')

    // 중복확인 상태를 업데이트하여 UI를 변경합니다.
    isDuplicateChecked.value = true
    isIdAvailable.value = result // 서버 응답 결과에 따라 아이디 사용 가능 여부를 설정합니다.
  } catch (error) {
    // 서버 연결 실패 또는 기타 오류 발생 시
    console.error('중복 체크 오류:', error)
    alert('서버 연결에 실패했습니다.')
    // 오류 발생 시 중복확인 상태를 초기화합니다.
    isDuplicateChecked.value = false
    isIdAvailable.value = false
  }
}

/**
 * 아이디 입력값이 변경될 때 호출되는 함수입니다.
 * 사용자가 아이디를 수정하면 중복확인 상태를 초기화하여 다시 확인하도록 만듭니다.
 */
const onLoginIdChange = () => {
  isDuplicateChecked.value = false
  isIdAvailable.value = false
}

/**
 * 회원가입을 처리하는 비동기 함수입니다.
 * 모든 유효성 검사를 통과하면 서버에 회원가입 요청을 보냅니다.
 */
const signup = async () => {
  // 모든 필수 필드가 입력되었는지 확인하는 1차 유효성 검사.
  if (!loginInfo.value.loginId.trim() || !loginInfo.value.nickname.trim() || !loginInfo.value.loginPw.trim() || !loginInfo.value.loginPwConfirm.trim()) {
    alert('모든 필드를 입력해주세요.')
    return
  }

  // 아이디 중복확인 여부와 사용 가능 상태를 확인하는 2차 유효성 검사.
  if (!isDuplicateChecked.value || !isIdAvailable.value) {
    alert('아이디 중복확인을 먼저 해주세요.')
    return
  }

  // 비밀번호와 비밀번호 확인 값이 일치하는지 확인하는 3차 유효성 검사.
  if (loginInfo.value.loginPw !== loginInfo.value.loginPwConfirm) {
    alert('비밀번호가 일치하지 않습니다.')
    return
  }

  try {
    // Axios를 사용해 백엔드의 'insert' API에 POST 요청을 보냅니다.
    // 회원가입에 필요한 아이디, 닉네임, 비밀번호 데이터를 전송합니다.
    const response = await axios.post('http://localhost:8080/api/insert', {
      loginId: loginInfo.value.loginId,
      nickname: loginInfo.value.nickname,
      loginPw: loginInfo.value.loginPw
    })

    // 서버 응답이 'success'이면 성공, 아니면 실패 처리.
    if (response.data === 'success') {
      alert('회원가입이 완료되었습니다!')
      router.push('/') // 회원가입 성공 후 메인(로그인) 페이지로 이동합니다.
    } else {
      alert('회원가입에 실패했습니다. 다시 시도해주세요.')
    }
  } catch (error) {
    // 서버 통신 중 오류 발생 시
    console.error('회원가입 오류:', error)
    alert('서버 연결에 실패했습니다.')
  }
}
</script>

<style scoped>
.signup-container {
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

.form-title {
  color: gray;
  font-weight: bold;
  margin-bottom: 10px;
}

.form-wrapper {
  display: flex;
  flex-direction: column;
  gap: 10px;
  width: 300px;
  align-items: center;
}

.input-row {
  display: flex;
  gap: 10px;
  align-items: center;
  width: 100%;
}

.input-field {
  flex: 1;
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

.check-btn {
  white-space: nowrap;
  /* 버튼 텍스트가 한 줄에 나오도록 설정 */
  cursor: pointer;
  border: none;
  padding: 5px 10px;
  border-radius: 4px;
  font-size: 12px;
  color: white;
  background-color: #007bff;
}

.btn-available {
  background-color: #28a745;
  /* 사용 가능 시 녹색 */
}

.btn-duplicate {
  background-color: #dc3545;
  /* 중복 시 빨간색 */
}

.signup-btn,
.login-redirect-btn {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
  color: white;
}

.signup-btn {
  background-color: #007bff;
}

.login-redirect-btn {
  background-color: #6c757d;
}
</style>
