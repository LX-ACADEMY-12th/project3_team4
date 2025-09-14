<template>
  <div class="container">
    <div class="header">
      <div class="logo-wrapper">
        <img src="@/assets/images/lx.png" class="logo-img">
      </div>
      <div class="title">XIIWORLD</div>
      <div class="team">team4</div>
    </div>

    <div class="form-wrapper">
      <div class="input-row">
        <input type="text" placeholder="아이디(이메일)" v-model="loginData.loginId" class="input-id">
      </div> 
     
      <div class="input-container">
        <input type="password" placeholder="비밀번호" v-model="loginData.loginPw" class="input-pw">
      </div>

      <div class="button-row">
        <input type="button" value="로그인" @click="handleLogin" class="login-btn">
        <input type="button" value="회원가입" @click="$router.push('/')" class="signup-btn">
      </div>
    </div>
  </div>
</template>



<script setup>
import axios from 'axios'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()

const loginData = ref({
  loginId: '',
  loginPw: ''
})

async function handleLogin() {
  if(!loginData.value.loginId.trim() || !loginData.value.loginPw.trim()) {
    alert('아이디와 비밀번호를 입력해주세요.')
    return
  }
  
  try {
    await axios.post('http://localhost:8080/api/login', {
      loginId: loginData.value.loginId,
      loginPw: loginData.value.loginPw
    })

    alert('로그인 성공')
    localStorage.setItem('loginId', loginData.value.loginId)
    router.push('/')

  } catch (error) {
    console.error('로그인 에러' + error)
    alert('로그인 실패')
  }
}
  
</script>

<style scoped>
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

.input-row {
  display: flex; 
  gap: 10px; 
  align-items: center;
}

.input-id {
  flex: 1;
}


.button-row {
  display: flex; 
  gap: 10px; 
  align-items: center;
}
</style>


