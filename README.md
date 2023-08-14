Chạy lệnh ở terminal trong 
cd ../../mock-enrick-skill/

Đổi lại password truy cập vào DB tuỳ theo cài đặt của bạn
trong file application.yml
username/password: postgres/2608

Run docker thường
docker-compose -f docker-compose.yml up

Run docker chạy ẩn
docker-compose -f docker-compose.yml up -d

Trước khi run chạy lệnh

mvn clean compile
Click button Run Or Debug project là chạy được.

