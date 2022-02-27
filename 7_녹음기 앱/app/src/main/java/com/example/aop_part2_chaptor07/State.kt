package com.example.aop_part2_chaptor07

enum class State {
    BEFORE_RECORDING,
    ON_RECORDING,
    AFTER_RECORDING,
    ON_PLAYING
}
// 녹음 상태값에 따라 다른 UI를 보여줘야하기 때문에 미리 정의 !