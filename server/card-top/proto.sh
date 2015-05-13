#!/bin/sh

echo $JAVA_HOME

CURRENT_DIR=`pwd`

cd $CURRENT_DIR/../../proto

protoc --java_out=$CURRENT_DIR/card-common/src/main/java game_msg.proto

cd $CURRENT_DIR