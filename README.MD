bật terminal dự án, paste vào để build:
javac -cp ".;libs/db4o-8.0.249.16098-all-java5.jar;libs/dRS-8.0.249.16098-core.jar;libs/h2-2.3.232.jar" -d bin src/\*.java


sau đó chạy lệnh sau để chạy dự án:
java -cp ".;libs/db4o-8.0.249.16098-all-java5.jar;libs/dRS-8.0.249.16098-core.jar;libs/h2-2.3.232.jar;bin" Main
