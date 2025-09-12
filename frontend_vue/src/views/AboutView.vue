<template>
  <div style="width: 400px; height: 400px; background-color: white; border-radius: 50%; display: flex; flex-direction: column; justify-content: center; align-items: center; padding: 40px; box-sizing: border-box;">

  <div style="display: flex; align-items: center;justify-content: center;  margin-bottom: 20px; gap: 15px">
    <div style="width: 40px; height: 40px; border-radius: 50%; background-color: black; border: 2px solid #ddd; display: flex; justify-content: center; align-items: center; margin: 0 auto 10px auto; overflow: hidden;">
      <img src="@/assets/images/lx.png" style="width: 40px; height: 40px; object-fit: contain;">
    
    </div>
    <div style="font-size:xx-large; font-weight: bold; color:gray;">XIIWORLD</div>
    <div style="color:gray; font-size: smaller;">team4</div>
  </div>

  <div style="display: flex; flex-direction: column; gap: 10px; width: 300px; align-items: center;">
  
    <div style="display: flex; gap: 10px; align-items: center;">
      <input type="text" placeholder="아이디(이메일)"  v-model = "loginData.loginId" 
      style="flex: 1; ">
    </div> 
    <div>
      <input type="password" placeholder="비밀번호" v-model="loginData.loginPw">
    </div>
     <div style="display: flex; gap: 10px; align-items: center;">
      <input type="button" value="로그인" @click="handleLogin">
      <input type="button" value="회원가입" @click="$router.push('/')">
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
    alert('로그인 실패')
  }
}
  
</script>

<style>

</style>
