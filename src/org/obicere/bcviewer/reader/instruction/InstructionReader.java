package org.obicere.bcviewer.reader.instruction;

import org.obicere.bcviewer.bytecode.instruction.Instruction;
import org.obicere.bcviewer.bytecode.instruction.UnknownInstruction;
import org.obicere.bcviewer.util.IndexedDataInputStream;
import org.obicere.bcviewer.util.MultiReader;
import org.obicere.bcviewer.util.Reader;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Obicere
 */
public class InstructionReader extends MultiReader<Integer, Instruction> {

    public static final int OPCODE_NOP             = 0x0;
    public static final int OPCODE_ACONST_NULL     = 0x1;
    public static final int OPCODE_ICONST_M1       = 0x2;
    public static final int OPCODE_ICONST_0        = 0x3;
    public static final int OPCODE_ICONST_1        = 0x4;
    public static final int OPCODE_ICONST_2        = 0x5;
    public static final int OPCODE_ICONST_3        = 0x6;
    public static final int OPCODE_ICONST_4        = 0x7;
    public static final int OPCODE_ICONST_5        = 0x8;
    public static final int OPCODE_LCONST_0        = 0x9;
    public static final int OPCODE_LCONST_1        = 0xa;
    public static final int OPCODE_FCONST_0        = 0xb;
    public static final int OPCODE_FCONST_1        = 0xc;
    public static final int OPCODE_FCONST_2        = 0xd;
    public static final int OPCODE_DCONST_0        = 0xe;
    public static final int OPCODE_DCONST_1        = 0xf;
    public static final int OPCODE_BIPUSH          = 0x10;
    public static final int OPCODE_SIPUSH          = 0x11;
    public static final int OPCODE_LDC             = 0x12;
    public static final int OPCODE_LDC_W           = 0x13;
    public static final int OPCODE_LDC2_W          = 0x14;
    public static final int OPCODE_ILOAD           = 0x15;
    public static final int OPCODE_LLOAD           = 0x16;
    public static final int OPCODE_FLOAD           = 0x17;
    public static final int OPCODE_DLOAD           = 0x18;
    public static final int OPCODE_ALOAD           = 0x19;
    public static final int OPCODE_ILOAD_0         = 0x1a;
    public static final int OPCODE_ILOAD_1         = 0x1b;
    public static final int OPCODE_ILOAD_2         = 0x1c;
    public static final int OPCODE_ILOAD_3         = 0x1d;
    public static final int OPCODE_LLOAD_0         = 0x1e;
    public static final int OPCODE_LLOAD_1         = 0x1f;
    public static final int OPCODE_LLOAD_2         = 0x20;
    public static final int OPCODE_LLOAD_3         = 0x21;
    public static final int OPCODE_FLOAD_0         = 0x22;
    public static final int OPCODE_FLOAD_1         = 0x23;
    public static final int OPCODE_FLOAD_2         = 0x24;
    public static final int OPCODE_FLOAD_3         = 0x25;
    public static final int OPCODE_DLOAD_0         = 0x26;
    public static final int OPCODE_DLOAD_1         = 0x27;
    public static final int OPCODE_DLOAD_2         = 0x28;
    public static final int OPCODE_DLOAD_3         = 0x29;
    public static final int OPCODE_ALOAD_0         = 0x2a;
    public static final int OPCODE_ALOAD_1         = 0x2b;
    public static final int OPCODE_ALOAD_2         = 0x2c;
    public static final int OPCODE_ALOAD_3         = 0x2d;
    public static final int OPCODE_IALOAD          = 0x2e;
    public static final int OPCODE_LALOAD          = 0x2f;
    public static final int OPCODE_FALOAD          = 0x30;
    public static final int OPCODE_DALOAD          = 0x31;
    public static final int OPCODE_AALOAD          = 0x32;
    public static final int OPCODE_BALOAD          = 0x33;
    public static final int OPCODE_CALOAD          = 0x34;
    public static final int OPCODE_SALOAD          = 0x35;
    public static final int OPCODE_ISTORE          = 0x36;
    public static final int OPCODE_LSTORE          = 0x37;
    public static final int OPCODE_FSTORE          = 0x38;
    public static final int OPCODE_DSTORE          = 0x39;
    public static final int OPCODE_ASTORE          = 0x3a;
    public static final int OPCODE_ISTORE_0        = 0x3b;
    public static final int OPCODE_ISTORE_1        = 0x3c;
    public static final int OPCODE_ISTORE_2        = 0x3d;
    public static final int OPCODE_ISTORE_3        = 0x3e;
    public static final int OPCODE_LSTORE_0        = 0x3f;
    public static final int OPCODE_LSTORE_1        = 0x40;
    public static final int OPCODE_LSTORE_2        = 0x41;
    public static final int OPCODE_LSTORE_3        = 0x42;
    public static final int OPCODE_FSTORE_0        = 0x43;
    public static final int OPCODE_FSTORE_1        = 0x44;
    public static final int OPCODE_FSTORE_2        = 0x45;
    public static final int OPCODE_FSTORE_3        = 0x46;
    public static final int OPCODE_DSTORE_0        = 0x47;
    public static final int OPCODE_DSTORE_1        = 0x48;
    public static final int OPCODE_DSTORE_2        = 0x49;
    public static final int OPCODE_DSTORE_3        = 0x4a;
    public static final int OPCODE_ASTORE_0        = 0x4b;
    public static final int OPCODE_ASTORE_1        = 0x4c;
    public static final int OPCODE_ASTORE_2        = 0x4d;
    public static final int OPCODE_ASTORE_3        = 0x4e;
    public static final int OPCODE_IASTORE         = 0x4f;
    public static final int OPCODE_LASTORE         = 0x50;
    public static final int OPCODE_FASTORE         = 0x51;
    public static final int OPCODE_DASTORE         = 0x52;
    public static final int OPCODE_AASTORE         = 0x53;
    public static final int OPCODE_BASTORE         = 0x54;
    public static final int OPCODE_CASTORE         = 0x55;
    public static final int OPCODE_SASTORE         = 0x56;
    public static final int OPCODE_POP             = 0x57;
    public static final int OPCODE_POP2            = 0x58;
    public static final int OPCODE_DUP             = 0x59;
    public static final int OPCODE_DUP_X1          = 0x5a;
    public static final int OPCODE_DUP_X2          = 0x5b;
    public static final int OPCODE_DUP2            = 0x5c;
    public static final int OPCODE_DUP2_X1         = 0x5d;
    public static final int OPCODE_DUP2_X2         = 0x5e;
    public static final int OPCODE_SWAP            = 0x5f;
    public static final int OPCODE_IADD            = 0x60;
    public static final int OPCODE_LADD            = 0x61;
    public static final int OPCODE_FADD            = 0x62;
    public static final int OPCODE_DADD            = 0x63;
    public static final int OPCODE_ISUB            = 0x64;
    public static final int OPCODE_LSUB            = 0x65;
    public static final int OPCODE_FSUB            = 0x66;
    public static final int OPCODE_DSUB            = 0x67;
    public static final int OPCODE_IMUL            = 0x68;
    public static final int OPCODE_LMUL            = 0x69;
    public static final int OPCODE_FMUL            = 0x6a;
    public static final int OPCODE_DMUL            = 0x6b;
    public static final int OPCODE_IDIV            = 0x6c;
    public static final int OPCODE_LDIV            = 0x6d;
    public static final int OPCODE_FDIV            = 0x6e;
    public static final int OPCODE_DDIV            = 0x6f;
    public static final int OPCODE_IREM            = 0x70;
    public static final int OPCODE_LREM            = 0x71;
    public static final int OPCODE_FREM            = 0x72;
    public static final int OPCODE_DREM            = 0x73;
    public static final int OPCODE_INEG            = 0x74;
    public static final int OPCODE_LNEG            = 0x75;
    public static final int OPCODE_FNEG            = 0x76;
    public static final int OPCODE_DNEG            = 0x77;
    public static final int OPCODE_ISHL            = 0x78;
    public static final int OPCODE_LSHL            = 0x79;
    public static final int OPCODE_ISHR            = 0x7a;
    public static final int OPCODE_LSHR            = 0x7b;
    public static final int OPCODE_IUSHR           = 0x7c;
    public static final int OPCODE_LUSHR           = 0x7d;
    public static final int OPCODE_IAND            = 0x7e;
    public static final int OPCODE_LAND            = 0x7f;
    public static final int OPCODE_IOR             = 0x80;
    public static final int OPCODE_LOR             = 0x81;
    public static final int OPCODE_IXOR            = 0x82;
    public static final int OPCODE_LXOR            = 0x83;
    public static final int OPCODE_IINC            = 0x84;
    public static final int OPCODE_I2L             = 0x85;
    public static final int OPCODE_I2F             = 0x86;
    public static final int OPCODE_I2D             = 0x87;
    public static final int OPCODE_L2I             = 0x88;
    public static final int OPCODE_L2F             = 0x89;
    public static final int OPCODE_L2D             = 0x8a;
    public static final int OPCODE_F2I             = 0x8b;
    public static final int OPCODE_F2L             = 0x8c;
    public static final int OPCODE_F2D             = 0x8d;
    public static final int OPCODE_D2I             = 0x8e;
    public static final int OPCODE_D2L             = 0x8f;
    public static final int OPCODE_D2F             = 0x90;
    public static final int OPCODE_I2B             = 0x91;
    public static final int OPCODE_I2C             = 0x92;
    public static final int OPCODE_I2S             = 0x93;
    public static final int OPCODE_LCMP            = 0x94;
    public static final int OPCODE_FCMPL           = 0x95;
    public static final int OPCODE_FCMPG           = 0x96;
    public static final int OPCODE_DCMPL           = 0x97;
    public static final int OPCODE_DCMPG           = 0x98;
    public static final int OPCODE_IFEQ            = 0x99;
    public static final int OPCODE_IFNE            = 0x9a;
    public static final int OPCODE_IFLT            = 0x9b;
    public static final int OPCODE_IFGE            = 0x9c;
    public static final int OPCODE_IFGT            = 0x9d;
    public static final int OPCODE_IFLE            = 0x9e;
    public static final int OPCODE_IF_ICMPEQ       = 0x9f;
    public static final int OPCODE_IF_ICMPNE       = 0xa0;
    public static final int OPCODE_IF_ICMPLT       = 0xa1;
    public static final int OPCODE_IF_ICMPGE       = 0xa2;
    public static final int OPCODE_IF_ICMPGT       = 0xa3;
    public static final int OPCODE_IF_ICMPLE       = 0xa4;
    public static final int OPCODE_IF_ACMPEQ       = 0xa5;
    public static final int OPCODE_IF_ACMPNE       = 0xa6;
    public static final int OPCODE_GOTO            = 0xa7;
    public static final int OPCODE_JSR             = 0xa8;
    public static final int OPCODE_RET             = 0xa9;
    public static final int OPCODE_TABLESWITCH     = 0xaa;
    public static final int OPCODE_LOOKUPSWITCH    = 0xab;
    public static final int OPCODE_IRETURN         = 0xac;
    public static final int OPCODE_LRETURN         = 0xad;
    public static final int OPCODE_FRETURN         = 0xae;
    public static final int OPCODE_DRETURN         = 0xaf;
    public static final int OPCODE_ARETURN         = 0xb0;
    public static final int OPCODE_RETURN          = 0xb1;
    public static final int OPCODE_GETSTATIC       = 0xb2;
    public static final int OPCODE_PUTSTATIC       = 0xb3;
    public static final int OPCODE_GETFIELD        = 0xb4;
    public static final int OPCODE_PUTFIELD        = 0xb5;
    public static final int OPCODE_INVOKEVIRTUAL   = 0xb6;
    public static final int OPCODE_INVOKESPECIAL   = 0xb7;
    public static final int OPCODE_INVOKESTATIC    = 0xb8;
    public static final int OPCODE_INVOKEINTERFACE = 0xb9;
    public static final int OPCODE_INVOKEDYNAMIC   = 0xba;
    public static final int OPCODE_NEW             = 0xbb;
    public static final int OPCODE_NEWARRAY        = 0xbc;
    public static final int OPCODE_ANEWARRAY       = 0xbd;
    public static final int OPCODE_ARRAYLENGTH     = 0xbe;
    public static final int OPCODE_ATHROW          = 0xbf;
    public static final int OPCODE_CHECKCAST       = 0xc0;
    public static final int OPCODE_INSTANCEOF      = 0xc1;
    public static final int OPCODE_MONITORENTER    = 0xc2;
    public static final int OPCODE_MONITOREXIT     = 0xc3;
    public static final int OPCODE_WIDE            = 0xc4;
    public static final int OPCODE_MULTIANEWARRAY  = 0xc5;
    public static final int OPCODE_IFNULL          = 0xc6;
    public static final int OPCODE_IFNONNULL       = 0xc7;
    public static final int OPCODE_GOTO_W          = 0xc8;
    public static final int OPCODE_JSR_W           = 0xc9;
    // might be implemented if needed
    //public static final int OPCODE_BREAKPOINT      = 0xca;
    //public static final int OPCODE_IMPDEP1         = 0xfe;
    //public static final int OPCODE_IMPDEP2         = 0xff;

    public InstructionReader() {
        add(OPCODE_NOP, new nopReader());
        add(OPCODE_ACONST_NULL, new aconst_nullReader());
        add(OPCODE_ICONST_M1, new iconst_m1Reader());
        add(OPCODE_ICONST_0, new iconst_0Reader());
        add(OPCODE_ICONST_1, new iconst_1Reader());
        add(OPCODE_ICONST_2, new iconst_2Reader());
        add(OPCODE_ICONST_3, new iconst_3Reader());
        add(OPCODE_ICONST_4, new iconst_4Reader());
        add(OPCODE_ICONST_5, new iconst_5Reader());
        add(OPCODE_LCONST_0, new lconst_0Reader());
        add(OPCODE_LCONST_1, new lconst_1Reader());
        add(OPCODE_FCONST_0, new fconst_0Reader());
        add(OPCODE_FCONST_1, new fconst_1Reader());
        add(OPCODE_FCONST_2, new fconst_2Reader());
        add(OPCODE_DCONST_0, new dconst_0Reader());
        add(OPCODE_DCONST_1, new dconst_1Reader());
        add(OPCODE_BIPUSH, new bipushReader());
        add(OPCODE_SIPUSH, new sipushReader());
        add(OPCODE_LDC, new ldcReader());
        add(OPCODE_LDC_W, new ldc_wReader());
        add(OPCODE_LDC2_W, new ldc2_wReader());
        add(OPCODE_ILOAD, new iloadReader());
        add(OPCODE_LLOAD, new lloadReader());
        add(OPCODE_FLOAD, new floadReader());
        add(OPCODE_DLOAD, new dloadReader());
        add(OPCODE_ALOAD, new aloadReader());
        add(OPCODE_ILOAD_0, new iload_0Reader());
        add(OPCODE_ILOAD_1, new iload_1Reader());
        add(OPCODE_ILOAD_2, new iload_2Reader());
        add(OPCODE_ILOAD_3, new iload_3Reader());
        add(OPCODE_LLOAD_0, new lload_0Reader());
        add(OPCODE_LLOAD_1, new lload_1Reader());
        add(OPCODE_LLOAD_2, new lload_2Reader());
        add(OPCODE_LLOAD_3, new lload_3Reader());
        add(OPCODE_FLOAD_0, new fload_0Reader());
        add(OPCODE_FLOAD_1, new fload_1Reader());
        add(OPCODE_FLOAD_2, new fload_2Reader());
        add(OPCODE_FLOAD_3, new fload_3Reader());
        add(OPCODE_DLOAD_0, new dload_0Reader());
        add(OPCODE_DLOAD_1, new dload_1Reader());
        add(OPCODE_DLOAD_2, new dload_2Reader());
        add(OPCODE_DLOAD_3, new dload_3Reader());
        add(OPCODE_ALOAD_0, new aload_0Reader());
        add(OPCODE_ALOAD_1, new aload_1Reader());
        add(OPCODE_ALOAD_2, new aload_2Reader());
        add(OPCODE_ALOAD_3, new aload_3Reader());
        add(OPCODE_IALOAD, new ialoadReader());
        add(OPCODE_LALOAD, new laloadReader());
        add(OPCODE_FALOAD, new faloadReader());
        add(OPCODE_DALOAD, new daloadReader());
        add(OPCODE_AALOAD, new aaloadReader());
        add(OPCODE_BALOAD, new baloadReader());
        add(OPCODE_CALOAD, new caloadReader());
        add(OPCODE_SALOAD, new saloadReader());
        add(OPCODE_ISTORE, new istoreReader());
        add(OPCODE_LSTORE, new lstoreReader());
        add(OPCODE_FSTORE, new fstoreReader());
        add(OPCODE_DSTORE, new dstoreReader());
        add(OPCODE_ASTORE, new astoreReader());
        add(OPCODE_ISTORE_0, new istore_0Reader());
        add(OPCODE_ISTORE_1, new istore_1Reader());
        add(OPCODE_ISTORE_2, new istore_2Reader());
        add(OPCODE_ISTORE_3, new istore_3Reader());
        add(OPCODE_LSTORE_0, new lstore_0Reader());
        add(OPCODE_LSTORE_1, new lstore_1Reader());
        add(OPCODE_LSTORE_2, new lstore_2Reader());
        add(OPCODE_LSTORE_3, new lstore_3Reader());
        add(OPCODE_FSTORE_0, new fstore_0Reader());
        add(OPCODE_FSTORE_1, new fstore_1Reader());
        add(OPCODE_FSTORE_2, new fstore_2Reader());
        add(OPCODE_FSTORE_3, new fstore_3Reader());
        add(OPCODE_DSTORE_0, new dstore_0Reader());
        add(OPCODE_DSTORE_1, new dstore_1Reader());
        add(OPCODE_DSTORE_2, new dstore_2Reader());
        add(OPCODE_DSTORE_3, new dstore_3Reader());
        add(OPCODE_ASTORE_0, new astore_0Reader());
        add(OPCODE_ASTORE_1, new astore_1Reader());
        add(OPCODE_ASTORE_2, new astore_2Reader());
        add(OPCODE_ASTORE_3, new astore_3Reader());
        add(OPCODE_IASTORE, new iastoreReader());
        add(OPCODE_LASTORE, new lastoreReader());
        add(OPCODE_FASTORE, new fastoreReader());
        add(OPCODE_DASTORE, new dastoreReader());
        add(OPCODE_AASTORE, new aastoreReader());
        add(OPCODE_BASTORE, new bastoreReader());
        add(OPCODE_CASTORE, new castoreReader());
        add(OPCODE_SASTORE, new sastoreReader());
        add(OPCODE_POP, new popReader());
        add(OPCODE_POP2, new pop2Reader());
        add(OPCODE_DUP, new dupReader());
        add(OPCODE_DUP_X1, new dup_x1Reader());
        add(OPCODE_DUP_X2, new dup_x2Reader());
        add(OPCODE_DUP2, new dup2Reader());
        add(OPCODE_DUP2_X1, new dup2_x1Reader());
        add(OPCODE_DUP2_X2, new dup2_x2Reader());
        add(OPCODE_SWAP, new swapReader());
        add(OPCODE_IADD, new iaddReader());
        add(OPCODE_LADD, new laddReader());
        add(OPCODE_FADD, new faddReader());
        add(OPCODE_DADD, new daddReader());
        add(OPCODE_ISUB, new isubReader());
        add(OPCODE_LSUB, new lsubReader());
        add(OPCODE_FSUB, new fsubReader());
        add(OPCODE_DSUB, new dsubReader());
        add(OPCODE_IMUL, new imulReader());
        add(OPCODE_LMUL, new lmulReader());
        add(OPCODE_FMUL, new fmulReader());
        add(OPCODE_DMUL, new dmulReader());
        add(OPCODE_IDIV, new idivReader());
        add(OPCODE_LDIV, new ldivReader());
        add(OPCODE_FDIV, new fdivReader());
        add(OPCODE_DDIV, new ddivReader());
        add(OPCODE_IREM, new iremReader());
        add(OPCODE_LREM, new lremReader());
        add(OPCODE_FREM, new fremReader());
        add(OPCODE_DREM, new dremReader());
        add(OPCODE_INEG, new inegReader());
        add(OPCODE_LNEG, new lnegReader());
        add(OPCODE_FNEG, new fnegReader());
        add(OPCODE_DNEG, new dnegReader());
        add(OPCODE_ISHL, new ishlReader());
        add(OPCODE_LSHL, new lshlReader());
        add(OPCODE_ISHR, new ishrReader());
        add(OPCODE_LSHR, new lshrReader());
        add(OPCODE_IUSHR, new iushrReader());
        add(OPCODE_LUSHR, new lushrReader());
        add(OPCODE_IAND, new iandReader());
        add(OPCODE_LAND, new landReader());
        add(OPCODE_IOR, new iorReader());
        add(OPCODE_LOR, new lorReader());
        add(OPCODE_IXOR, new ixorReader());
        add(OPCODE_LXOR, new lxorReader());
        add(OPCODE_IINC, new iincReader());
        add(OPCODE_I2L, new i2lReader());
        add(OPCODE_I2F, new i2fReader());
        add(OPCODE_I2D, new i2dReader());
        add(OPCODE_L2I, new l2iReader());
        add(OPCODE_L2F, new l2fReader());
        add(OPCODE_L2D, new l2dReader());
        add(OPCODE_F2I, new f2iReader());
        add(OPCODE_F2L, new f2lReader());
        add(OPCODE_F2D, new f2dReader());
        add(OPCODE_D2I, new d2iReader());
        add(OPCODE_D2L, new d2lReader());
        add(OPCODE_D2F, new d2fReader());
        add(OPCODE_I2B, new i2bReader());
        add(OPCODE_I2C, new i2cReader());
        add(OPCODE_I2S, new i2sReader());
        add(OPCODE_LCMP, new lcmpReader());
        add(OPCODE_FCMPL, new fcmplReader());
        add(OPCODE_FCMPG, new fcmpgReader());
        add(OPCODE_DCMPL, new dcmplReader());
        add(OPCODE_DCMPG, new dcmpgReader());
        add(OPCODE_IFEQ, new ifeqReader());
        add(OPCODE_IFNE, new ifneReader());
        add(OPCODE_IFLT, new ifltReader());
        add(OPCODE_IFGE, new ifgeReader());
        add(OPCODE_IFGT, new ifgtReader());
        add(OPCODE_IFLE, new ifleReader());
        add(OPCODE_IF_ICMPEQ, new if_icmpeqReader());
        add(OPCODE_IF_ICMPNE, new if_icmpneReader());
        add(OPCODE_IF_ICMPLT, new if_icmpltReader());
        add(OPCODE_IF_ICMPGE, new if_icmpgeReader());
        add(OPCODE_IF_ICMPGT, new if_icmpgtReader());
        add(OPCODE_IF_ICMPLE, new if_icmpleReader());
        add(OPCODE_IF_ACMPEQ, new if_acmpeqReader());
        add(OPCODE_IF_ACMPNE, new if_acmpneReader());
        add(OPCODE_GOTO, new gotoReader());
        add(OPCODE_JSR, new jsrReader());
        add(OPCODE_RET, new retReader());
        add(OPCODE_TABLESWITCH, new tableswitchReader());
        add(OPCODE_LOOKUPSWITCH, new lookupswitchReader());
        add(OPCODE_IRETURN, new ireturnReader());
        add(OPCODE_LRETURN, new lreturnReader());
        add(OPCODE_FRETURN, new freturnReader());
        add(OPCODE_DRETURN, new dreturnReader());
        add(OPCODE_ARETURN, new areturnReader());
        add(OPCODE_RETURN, new returnReader());
        add(OPCODE_GETSTATIC, new getstaticReader());
        add(OPCODE_PUTSTATIC, new putstaticReader());
        add(OPCODE_GETFIELD, new getfieldReader());
        add(OPCODE_PUTFIELD, new putfieldReader());
        add(OPCODE_INVOKEVIRTUAL, new invokevirtualReader());
        add(OPCODE_INVOKESPECIAL, new invokespecialReader());
        add(OPCODE_INVOKESTATIC, new invokestaticReader());
        add(OPCODE_INVOKEINTERFACE, new invokeinterfaceReader());
        add(OPCODE_INVOKEDYNAMIC, new invokedynamicReader());
        add(OPCODE_NEW, new newReader());
        add(OPCODE_NEWARRAY, new newarrayReader());
        add(OPCODE_ANEWARRAY, new anewarrayReader());
        add(OPCODE_ARRAYLENGTH, new arraylengthReader());
        add(OPCODE_ATHROW, new athrowReader());
        add(OPCODE_CHECKCAST, new checkcastReader());
        add(OPCODE_INSTANCEOF, new instanceofReader());
        add(OPCODE_MONITORENTER, new monitorenterReader());
        add(OPCODE_MONITOREXIT, new monitorexitReader());
        add(OPCODE_WIDE, new wideReader());
        add(OPCODE_MULTIANEWARRAY, new multianewarrayReader());
        add(OPCODE_IFNULL, new ifnullReader());
        add(OPCODE_IFNONNULL, new ifnonnullReader());
        add(OPCODE_GOTO_W, new goto_wReader());
        add(OPCODE_JSR_W, new jsr_wReader());
        //add(OPCODE_BREAKPOINT, new breakpointReader());
        //add(OPCODE_IMPDEP1, new impdep1Reader());
        //add(OPCODE_IMPDEP2, new impdep2Reader());
    }

    @Override
    public Instruction read(final IndexedDataInputStream input) throws IOException {
        final int start = input.getLogicalIndex();
        final int next = input.readUnsignedByte();
        final Reader<? extends Instruction> reader = get(next);
        // if there is no reader associated to this opcode we have an unknown op
        final Instruction instruction;
        if (reader == null) {
            Logger.getGlobal().log(Level.WARNING, "Found an unknown instruction with operand code: " + next + " at " + start);
            instruction = new UnknownInstruction();
        } else {
            instruction = reader.read(input);
        }

        // make sure to set start and end indices
        final int end = input.getLogicalIndex();
        instruction.setBounds(start, end);
        return instruction;
    }
}
