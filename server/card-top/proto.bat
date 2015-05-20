e:
cd %~dp0
cd ../../../
cd card_game
cd proto

set Path=%Path%;%cd%

protoc_java --java_out="%~dp0card-common/src/main/java" msg_struct.proto
protoc_java --java_out="%~dp0card-common/src/main/java" game_msg.proto

pause