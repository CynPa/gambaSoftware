/*   1:    */ package ec.gob.sri.comprobantes.ws.autorizacion;
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
/*  12:    */ public class RespuestaComprobante
/*  13:    */   implements Serializable
/*  14:    */ {
/*  15:    */   private String claveAccesoConsultada;
/*  16:    */   private String numeroComprobantes;
/*  17:    */   private RespuestaComprobanteAutorizaciones autorizaciones;
/*  18:    */   
/*  19:    */   public RespuestaComprobante(String claveAccesoConsultada, String numeroComprobantes, RespuestaComprobanteAutorizaciones autorizaciones)
/*  20:    */   {
/*  21: 22 */     this.claveAccesoConsultada = claveAccesoConsultada;
/*  22: 23 */     this.numeroComprobantes = numeroComprobantes;
/*  23: 24 */     this.autorizaciones = autorizaciones;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public String getClaveAccesoConsultada()
/*  27:    */   {
/*  28: 34 */     return this.claveAccesoConsultada;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setClaveAccesoConsultada(String claveAccesoConsultada)
/*  32:    */   {
/*  33: 44 */     this.claveAccesoConsultada = claveAccesoConsultada;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getNumeroComprobantes()
/*  37:    */   {
/*  38: 54 */     return this.numeroComprobantes;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setNumeroComprobantes(String numeroComprobantes)
/*  42:    */   {
/*  43: 64 */     this.numeroComprobantes = numeroComprobantes;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public RespuestaComprobanteAutorizaciones getAutorizaciones()
/*  47:    */   {
/*  48: 74 */     return this.autorizaciones;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setAutorizaciones(RespuestaComprobanteAutorizaciones autorizaciones)
/*  52:    */   {
/*  53: 84 */     this.autorizaciones = autorizaciones;
/*  54:    */   }
/*  55:    */   
/*  56: 87 */   private Object __equalsCalc = null;
/*  57:    */   
/*  58:    */   public synchronized boolean equals(Object obj)
/*  59:    */   {
/*  60: 89 */     if (!(obj instanceof RespuestaComprobante)) {
/*  61: 89 */       return false;
/*  62:    */     }
/*  63: 90 */     RespuestaComprobante other = (RespuestaComprobante)obj;
/*  64: 91 */     if (obj == null) {
/*  65: 91 */       return false;
/*  66:    */     }
/*  67: 92 */     if (this == obj) {
/*  68: 92 */       return true;
/*  69:    */     }
/*  70: 93 */     if (this.__equalsCalc != null) {
/*  71: 94 */       return this.__equalsCalc == obj;
/*  72:    */     }
/*  73: 96 */     this.__equalsCalc = obj;
/*  74: 98 */     if ((this.claveAccesoConsultada != null) || 
/*  75: 99 */       (other.getClaveAccesoConsultada() != null))
/*  76:    */     {
/*  77: 99 */       if (this.claveAccesoConsultada != null) {
/*  78:101 */         if (!this.claveAccesoConsultada.equals(other.getClaveAccesoConsultada())) {}
/*  79:    */       }
/*  80:    */     }
/*  81:101 */     else if ((this.numeroComprobantes != null) || 
/*  82:102 */       (other.getNumeroComprobantes() != null))
/*  83:    */     {
/*  84:102 */       if (this.numeroComprobantes != null) {
/*  85:104 */         if (!this.numeroComprobantes.equals(other.getNumeroComprobantes())) {}
/*  86:    */       }
/*  87:    */     }
/*  88:104 */     else if ((this.autorizaciones != null) || 
/*  89:105 */       (other.getAutorizaciones() != null)) {
/*  90:105 */       if (this.autorizaciones == null) {
/*  91:    */         break label162;
/*  92:    */       }
/*  93:    */     }
/*  94:    */     label162:
/*  95:107 */     boolean _equals = this.autorizaciones.equals(other.getAutorizaciones());
/*  96:108 */     this.__equalsCalc = null;
/*  97:109 */     return _equals;
/*  98:    */   }
/*  99:    */   
/* 100:112 */   private boolean __hashCodeCalc = false;
/* 101:    */   
/* 102:    */   public synchronized int hashCode()
/* 103:    */   {
/* 104:114 */     if (this.__hashCodeCalc) {
/* 105:115 */       return 0;
/* 106:    */     }
/* 107:117 */     this.__hashCodeCalc = true;
/* 108:118 */     int _hashCode = 1;
/* 109:119 */     if (getClaveAccesoConsultada() != null) {
/* 110:120 */       _hashCode += getClaveAccesoConsultada().hashCode();
/* 111:    */     }
/* 112:122 */     if (getNumeroComprobantes() != null) {
/* 113:123 */       _hashCode += getNumeroComprobantes().hashCode();
/* 114:    */     }
/* 115:125 */     if (getAutorizaciones() != null) {
/* 116:126 */       _hashCode += getAutorizaciones().hashCode();
/* 117:    */     }
/* 118:128 */     this.__hashCodeCalc = false;
/* 119:129 */     return _hashCode;
/* 120:    */   }
/* 121:    */   
/* 122:133 */   private static TypeDesc typeDesc = new TypeDesc(RespuestaComprobante.class, true);
/* 123:    */   
/* 124:    */   static
/* 125:    */   {
/* 126:137 */     typeDesc.setXmlType(new QName("http://ec.gob.sri.ws.autorizacion", "respuestaComprobante"));
/* 127:138 */     ElementDesc elemField = new ElementDesc();
/* 128:139 */     elemField.setFieldName("claveAccesoConsultada");
/* 129:140 */     elemField.setXmlName(new QName("", "claveAccesoConsultada"));
/* 130:141 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 131:142 */     elemField.setMinOccurs(0);
/* 132:143 */     elemField.setNillable(false);
/* 133:144 */     typeDesc.addFieldDesc(elemField);
/* 134:145 */     elemField = new ElementDesc();
/* 135:146 */     elemField.setFieldName("numeroComprobantes");
/* 136:147 */     elemField.setXmlName(new QName("", "numeroComprobantes"));
/* 137:148 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 138:149 */     elemField.setMinOccurs(0);
/* 139:150 */     elemField.setNillable(false);
/* 140:151 */     typeDesc.addFieldDesc(elemField);
/* 141:152 */     elemField = new ElementDesc();
/* 142:153 */     elemField.setFieldName("autorizaciones");
/* 143:154 */     elemField.setXmlName(new QName("", "autorizaciones"));
/* 144:155 */     elemField.setXmlType(new QName("http://ec.gob.sri.ws.autorizacion", ">respuestaComprobante>autorizaciones"));
/* 145:156 */     elemField.setMinOccurs(0);
/* 146:157 */     elemField.setNillable(false);
/* 147:158 */     typeDesc.addFieldDesc(elemField);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public static TypeDesc getTypeDesc()
/* 151:    */   {
/* 152:165 */     return typeDesc;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/* 156:    */   {
/* 157:175 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/* 161:    */   {
/* 162:187 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public RespuestaComprobante() {}
/* 166:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.gob.sri.comprobantes.ws.autorizacion.RespuestaComprobante
 * JD-Core Version:    0.7.0.1
 */