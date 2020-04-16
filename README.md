# 로그 데이터 처리 프로젝트
로그 파일을 분석하여 단위 시간 별 통계 파일을 생성하는 프로그램 개발하는 프로젝트입니다.


![diagram](https://user-images.githubusercontent.com/52955185/76828899-4e211d00-6865-11ea-913b-11f0e3159382.jpg)


## 프로젝트 설명
### model
* Log
    * 모델 객체
* LogWithCount
    * Log와 Count를 묶은 모델 객체
### service
* Main
    * Main method
* LogAccessor
    * log 파일에 접근하여 분, 시, 일 파일을 만드는 methods
* LogAccessorRepeator
    * 실시간으로 입력되는 log를 분, 시, 일 파일에 반복해서 추가하는 methods
* LogFileService
    * log 파일 입출력 및 count 체크 관련 methods
* LogParser
    * log parsing 관련 methods
* TraceFileService
    * trace 파일 입출력 관련 methods
### logfile
* access
    * 예시 원본 log 파일
* minute
    * 예시 분 단위 결과 파일
* hour
    * 예시 시 단위 결과 파일
* day
    * 예시 일 단위 결과 파일
* trace
    * 가장 최근에 읽은 pointer 위치를 저장하는 파일

