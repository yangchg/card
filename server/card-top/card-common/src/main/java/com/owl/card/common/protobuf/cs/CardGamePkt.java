// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: game_msg.proto

package com.owl.card.common.protobuf.cs;

public final class CardGamePkt {
  private CardGamePkt() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  static com.google.protobuf.Descriptors.Descriptor
    internal_static_UserLoginC2S_descriptor;
  static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_UserLoginC2S_fieldAccessorTable;
  static com.google.protobuf.Descriptors.Descriptor
    internal_static_UserLoginS2C_descriptor;
  static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_UserLoginS2C_fieldAccessorTable;
  static com.google.protobuf.Descriptors.Descriptor
    internal_static_CardGroupAdd_descriptor;
  static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_CardGroupAdd_fieldAccessorTable;
  static com.google.protobuf.Descriptors.Descriptor
    internal_static_CardGroupAddRt_descriptor;
  static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_CardGroupAddRt_fieldAccessorTable;
  static com.google.protobuf.Descriptors.Descriptor
    internal_static_CardGroupEdit_descriptor;
  static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_CardGroupEdit_fieldAccessorTable;
  static com.google.protobuf.Descriptors.Descriptor
    internal_static_CardGroupEditRt_descriptor;
  static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_CardGroupEditRt_fieldAccessorTable;
  static com.google.protobuf.Descriptors.Descriptor
    internal_static_CardGroupDel_descriptor;
  static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_CardGroupDel_fieldAccessorTable;
  static com.google.protobuf.Descriptors.Descriptor
    internal_static_CardGroupDelRt_descriptor;
  static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_CardGroupDelRt_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\016game_msg.proto\032\020msg_struct.proto\"=\n\014Us" +
      "erLoginC2S\022\r\n\005accid\030\001 \002(\005\022\016\n\006tstamp\030\002 \002(" +
      "\005\022\016\n\006ticket\030\003 \002(\t\";\n\014UserLoginS2C\022\n\n\002rt\030" +
      "\001 \002(\005\022\037\n\010roleInfo\030\002 \001(\0132\r.RoleInfoData\"D" +
      "\n\014CardGroupAdd\022\014\n\004hero\030\001 \002(\005\022\021\n\tgroupNam" +
      "e\030\002 \002(\t\022\023\n\013cardProtoId\030\003 \003(\005\"R\n\016CardGrou" +
      "pAddRt\022\n\n\002rt\030\001 \002(\005\022\014\n\004hero\030\002 \001(\005\022\021\n\tgrou" +
      "pName\030\003 \001(\t\022\023\n\013cardProtoId\030\004 \003(\005\"H\n\rCard" +
      "GroupEdit\022\017\n\007groupId\030\001 \002(\003\022\021\n\tgroupName\030" +
      "\002 \002(\t\022\023\n\013cardProtoId\030\003 \003(\005\"V\n\017CardGroupE",
      "ditRt\022\n\n\002rt\030\001 \002(\005\022\017\n\007groupId\030\002 \001(\003\022\021\n\tgr" +
      "oupName\030\003 \001(\t\022\023\n\013cardProtoId\030\004 \003(\005\"\037\n\014Ca" +
      "rdGroupDel\022\017\n\007groupId\030\001 \002(\003\"-\n\016CardGroup" +
      "DelRt\022\n\n\002rt\030\001 \002(\005\022\017\n\007groupId\030\002 \002(\003B0\n\037co" +
      "m.owl.card.common.protobuf.csB\013CardGameP" +
      "ktP\001"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_UserLoginC2S_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_UserLoginC2S_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_UserLoginC2S_descriptor,
              new java.lang.String[] { "Accid", "Tstamp", "Ticket", });
          internal_static_UserLoginS2C_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_UserLoginS2C_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_UserLoginS2C_descriptor,
              new java.lang.String[] { "Rt", "RoleInfo", });
          internal_static_CardGroupAdd_descriptor =
            getDescriptor().getMessageTypes().get(2);
          internal_static_CardGroupAdd_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_CardGroupAdd_descriptor,
              new java.lang.String[] { "Hero", "GroupName", "CardProtoId", });
          internal_static_CardGroupAddRt_descriptor =
            getDescriptor().getMessageTypes().get(3);
          internal_static_CardGroupAddRt_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_CardGroupAddRt_descriptor,
              new java.lang.String[] { "Rt", "Hero", "GroupName", "CardProtoId", });
          internal_static_CardGroupEdit_descriptor =
            getDescriptor().getMessageTypes().get(4);
          internal_static_CardGroupEdit_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_CardGroupEdit_descriptor,
              new java.lang.String[] { "GroupId", "GroupName", "CardProtoId", });
          internal_static_CardGroupEditRt_descriptor =
            getDescriptor().getMessageTypes().get(5);
          internal_static_CardGroupEditRt_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_CardGroupEditRt_descriptor,
              new java.lang.String[] { "Rt", "GroupId", "GroupName", "CardProtoId", });
          internal_static_CardGroupDel_descriptor =
            getDescriptor().getMessageTypes().get(6);
          internal_static_CardGroupDel_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_CardGroupDel_descriptor,
              new java.lang.String[] { "GroupId", });
          internal_static_CardGroupDelRt_descriptor =
            getDescriptor().getMessageTypes().get(7);
          internal_static_CardGroupDelRt_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_CardGroupDelRt_descriptor,
              new java.lang.String[] { "Rt", "GroupId", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.owl.card.common.protobuf.struct.CardStructPkt.getDescriptor(),
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
