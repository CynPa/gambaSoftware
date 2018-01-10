/*   1:    */ package ec.gob.sri.comprobantes.ws.recepcion;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import javax.xml.namespace.QName;
/*   5:    */ import org.apache.axis.description.ElementDesc;
/*   6:    */ import org.apache.axis.description.TypeDesc;
/*   7:    */ import org.apache.axis.encoding.Deserializer;
/*   8:    */ import org.apache.axis.encoding.Serializer;
/*   9:    */ import org.apache.axis.encoding.ser.BeanDeserializer;
/*  10:    */ import org.apache.axis.encoding.ser.BeanSerializer;
/*  11:    */ 
/*  12:    */ public class RespuestaSolicitud
/*  13:    */   implements Serializable
/*  14:    */ {
/*  15:    */   private String estado;
/*  16:    */   private RespuestaSolicitudComprobantes comprobantes;
/*  17:    */   
/*  18:    */   public RespuestaSolicitud(String estado, RespuestaSolicitudComprobantes comprobantes)
/*  19:    */   {
/*  20: 20 */     this.estado = estado;
/*  21: 21 */     this.comprobantes = comprobantes;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public String getEstado()
/*  25:    */   {
/*  26: 31 */     return this.estado;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setEstado(String estado)
/*  30:    */   {
/*  31: 41 */     this.estado = estado;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public RespuestaSolicitudComprobantes getComprobantes()
/*  35:    */   {
/*  36: 51 */     return this.comprobantes;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setComprobantes(RespuestaSolicitudComprobantes comprobantes)
/*  40:    */   {
/*  41: 61 */     this.comprobantes = comprobantes;
/*  42:    */   }
/*  43:    */   
/*  44: 64 */   private Object __equalsCalc = null;
/*  45:    */   
/*  46:    */   public synchronized boolean equals(Object obj)
/*  47:    */   {
/*  48: 66 */     if (!(obj instanceof RespuestaSolicitud)) {
/*  49: 66 */       return false;
/*  50:    */     }
/*  51: 67 */     RespuestaSolicitud other = (RespuestaSolicitud)obj;
/*  52: 68 */     if (obj == null) {
/*  53: 68 */       return false;
/*  54:    */     }
/*  55: 69 */     if (this == obj) {
/*  56: 69 */       return true;
/*  57:    */     }
/*  58: 70 */     if (this.__equalsCalc != null) {
/*  59: 71 */       return this.__equalsCalc == obj;
/*  60:    */     }
/*  61: 73 */     this.__equalsCalc = obj;
/*  62: 75 */     if ((this.estado != null) || 
/*  63: 76 */       (other.getEstado() != null))
/*  64:    */     {
/*  65: 76 */       if (this.estado != null) {
/*  66: 78 */         if (!this.estado.equals(other.getEstado())) {}
/*  67:    */       }
/*  68:    */     }
/*  69: 78 */     else if ((this.comprobantes != null) || 
/*  70: 79 */       (other.getComprobantes() != null)) {
/*  71: 79 */       if (this.comprobantes == null) {
/*  72:    */         break label127;
/*  73:    */       }
/*  74:    */     }
/*  75:    */     label127:
/*  76: 81 */     boolean _equals = this.comprobantes.equals(other.getComprobantes());
/*  77: 82 */     this.__equalsCalc = null;
/*  78: 83 */     return _equals;
/*  79:    */   }
/*  80:    */   
/*  81: 86 */   private boolean __hashCodeCalc = false;
/*  82:    */   
/*  83:    */   public synchronized int hashCode()
/*  84:    */   {
/*  85: 88 */     if (this.__hashCodeCalc) {
/*  86: 89 */       return 0;
/*  87:    */     }
/*  88: 91 */     this.__hashCodeCalc = true;
/*  89: 92 */     int _hashCode = 1;
/*  90: 93 */     if (getEstado() != null) {
/*  91: 94 */       _hashCode += getEstado().hashCode();
/*  92:    */     }
/*  93: 96 */     if (getComprobantes() != null) {
/*  94: 97 */       _hashCode += getComprobantes().hashCode();
/*  95:    */     }
/*  96: 99 */     this.__hashCodeCalc = false;
/*  97:100 */     return _hashCode;
/*  98:    */   }
/*  99:    */   
/* 100:104 */   private static TypeDesc typeDesc = new TypeDesc(RespuestaSolicitud.class, true);
/* 101:    */   
/* 102:    */   static
/* 103:    */   {
/* 104:108 */     typeDesc.setXmlType(new QName("http://ec.gob.sri.ws.recepcion", "respuestaSolicitud"));
/* 105:109 */     ElementDesc elemField = new ElementDesc();
/* 106:110 */     elemField.setFieldName("estado");
/* 107:111 */     elemField.setXmlName(new QName("", "estado"));
/* 108:112 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 109:113 */     elemField.setMinOccurs(0);
/* 110:114 */     elemField.setNillable(false);
/* 111:115 */     typeDesc.addFieldDesc(elemField);
/* 112:116 */     elemField = new ElementDesc();
/* 113:117 */     elemField.setFieldName("comprobantes");
/* 114:118 */     elemField.setXmlName(new QName("", "comprobantes"));
/* 115:119 */     elemField.setXmlType(new QName("http://ec.gob.sri.ws.recepcion", ">respuestaSolicitud>comprobantes"));
/* 116:120 */     elemField.setMinOccurs(0);
/* 117:121 */     elemField.setNillable(false);
/* 118:122 */     typeDesc.addFieldDesc(elemField);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static TypeDesc getTypeDesc()
/* 122:    */   {
/* 123:129 */     return typeDesc;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/* 127:    */   {
/* 128:139 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/* 132:    */   {
/* 133:151 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public RespuestaSolicitud() {}
/* 137:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.gob.sri.comprobantes.ws.recepcion.RespuestaSolicitud
 * JD-Core Version:    0.7.0.1
 */