<template>
  <ul class="pagination pagination-circle pagination-outline">
    <!-- 처음 -->
    <li class="page-item first m-1" :class="{ disabled: isFirst }">
      <button
        class="page-link px-0"
        type="button"
        :disabled="isFirst"
        @click="!isFirst && requestFunc(1, pagination.perPage)"
        aria-label="첫 페이지"
      >
        <i class="ki-duotone ki-double-left fs-2"><span class="path1"></span><span class="path2"></span></i>
      </button>
    </li>

    <!-- 이전 -->
    <li class="page-item previous m-1" :class="{ disabled: isFirst }">
      <button
        class="page-link px-0"
        type="button"
        :disabled="isFirst"
        @click="!isFirst && requestFunc(pagination.page - 1, pagination.perPage)"
        aria-label="이전 페이지"
      >
        <i class="ki-duotone ki-left fs-2"></i>
      </button>
    </li>

    <!-- 페이지 번호 -->
    <li
      v-for="item in pagination.pages"
      :key="item.pageNo"
      class="page-item m-1"
      :class="{ active: item.pageActive }"
    >
      <button
        v-if="item.pageActive"
        class="page-link"
        type="button"
        disabled
        aria-current="page"
      >
        {{ item.pageNo }}
      </button>
      <button
        v-else
        class="page-link"
        type="button"
        @click="requestFunc(item.pageNo, pagination.perPage)"
      >
        {{ item.pageNo }}
      </button>
    </li>

    <!-- 다음 -->
    <li class="page-item next m-1" :class="{ disabled: isLast }">
      <button
        class="page-link px-0"
        type="button"
        :disabled="isLast"
        @click="!isLast && requestFunc(pagination.page + 1, pagination.perPage)"
        aria-label="다음 페이지"
      >
        <i class="ki-duotone ki-right fs-2"></i>
      </button>
    </li>

    <!-- 끝 -->
    <li class="page-item last m-1" :class="{ disabled: isLast }">
      <button
        class="page-link px-0"
        type="button"
        :disabled="isLast"
        @click="!isLast && requestFunc(pagination.pageCount, pagination.perPage)"
        aria-label="마지막 페이지"
      >
        <i class="ki-duotone ki-double-right fs-2"><span class="path1"></span><span class="path2"></span></i>
      </button>
    </li>
  </ul>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  requestFunc: { type: Function, required: true },
  pagination: {
    type: Object,
    required: true,
    // 최소 필수 키 유효성 체크(린트 경고 방지)
    validator(v) {
      return (
        v &&
        typeof v.page === 'number' &&
        typeof v.pageCount === 'number' &&
        typeof v.perPage === 'number' &&
        Array.isArray(v.pages)
      )
    }
  }
})

const isFirst = computed(() => props.pagination.page === 1)
const isLast  = computed(() => props.pagination.page >= props.pagination.pageCount)
const { requestFunc, pagination } = props
</script>

<style scoped>
/* 부트스트랩 pagination의 disabled 시 포인터 차단 및 시각적 처리 */
.page-item.disabled .page-link {
  pointer-events: none;
  opacity: .6;
}
</style>
