/*   1:    */ package ec.gob.sri.comprobantes.ws.autorizacion;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.Array;
/*   5:    */ import java.util.Arrays;
/*   6:    */ import javax.xml.namespace.QName;
/*   7:    */ import org.apache.axis.description.ElementDesc;
/*   8:    */ import org.apache.axis.description.TypeDesc;
/*   9:    */ import org.apache.axis.encoding.Deserializer;
/*  10:    */ import org.apache.axis.encoding.Serializer;
/*  11:    */ import org.apache.axis.encoding.ser.BeanDeserializer;
/*  12:    */ import org.apache.axis.encoding.ser.BeanSerializer;
/*  13:    */ 
/*  14:    */ public class RespuestaLote
/*  15:    */   implements Serializable
/*  16:    */ {
/*  17:    */   private String claveAccesoLoteConsultada;
/*  18:    */   private String numeroComprobantesLote;
/*  19:    */   private Autorizacion[] autorizaciones;
/*  20:    */   
/*  21:    */   public RespuestaLote(String claveAccesoLoteConsultada, String numeroComprobantesLote, Autorizacion[] autorizaciones)
/*  22:    */   {
/*  23: 24 */     this.claveAccesoLoteConsultada = claveAccesoLoteConsultada;
/*  24: 25 */     this.numeroComprobantesLote = numeroComprobantesLote;
/*  25: 26 */     this.autorizaciones = autorizaciones;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String getClaveAccesoLoteConsultada()
/*  29:    */   {
/*  30: 36 */     return this.claveAccesoLoteConsultada;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setClaveAccesoLoteConsultada(String claveAccesoLoteConsultada)
/*  34:    */   {
/*  35: 46 */     this.claveAccesoLoteConsultada = claveAccesoLoteConsultada;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String getNumeroComprobantesLote()
/*  39:    */   {
/*  40: 56 */     return this.numeroComprobantesLote;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setNumeroComprobantesLote(String numeroComprobantesLote)
/*  44:    */   {
/*  45: 66 */     this.numeroComprobantesLote = numeroComprobantesLote;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Autorizacion[] getAutorizaciones()
/*  49:    */   {
/*  50: 76 */     return this.autorizaciones;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setAutorizaciones(Autorizacion[] autorizaciones)
/*  54:    */   {
/*  55: 86 */     this.autorizaciones = autorizaciones;
/*  56:    */   }
/*  57:    */   
/*  58: 89 */   private Object __equalsCalc = null;
/*  59:    */   
/*  60:    */   public synchronized boolean equals(Object obj)
/*  61:    */   {
/*  62: 91 */     if (!(obj instanceof RespuestaLote)) {
/*  63: 91 */       return false;
/*  64:    */     }
/*  65: 92 */     RespuestaLote other = (RespuestaLote)obj;
/*  66: 93 */     if (obj == null) {
/*  67: 93 */       return false;
/*  68:    */     }
/*  69: 94 */     if (this == obj) {
/*  70: 94 */       return true;
/*  71:    */     }
/*  72: 95 */     if (this.__equalsCalc != null) {
/*  73: 96 */       return this.__equalsCalc == obj;
/*  74:    */     }
/*  75: 98 */     this.__equalsCalc = obj;
/*  76:100 */     if ((this.claveAccesoLoteConsultada != null) || 
/*  77:101 */       (other.getClaveAccesoLoteConsultada() != null))
/*  78:    */     {
/*  79:101 */       if (this.claveAccesoLoteConsultada != null) {
/*  80:103 */         if (!this.claveAccesoLoteConsultada.equals(other.getClaveAccesoLoteConsultada())) {}
/*  81:    */       }
/*  82:    */     }
/*  83:103 */     else if ((this.numeroComprobantesLote != null) || 
/*  84:104 */       (other.getNumeroComprobantesLote() != null))
/*  85:    */     {
/*  86:104 */       if (this.numeroComprobantesLote != null) {
/*  87:106 */         if (!this.numeroComprobantesLote.equals(other.getNumeroComprobantesLote())) {}
/*  88:    */       }
/*  89:    */     }
/*  90:106 */     else if ((this.autorizaciones != null) || 
/*  91:107 */       (other.getAutorizaciones() != null)) {
/*  92:107 */       if (this.autorizaciones == null) {
/*  93:    */         break label162;
/*  94:    */       }
/*  95:    */     }
/*  96:    */     label162:
/*  97:109 */     boolean _equals = Arrays.equals(this.autorizaciones, other.getAutorizaciones());
/*  98:110 */     this.__equalsCalc = null;
/*  99:111 */     return _equals;
/* 100:    */   }
/* 101:    */   
/* 102:114 */   private boolean __hashCodeCalc = false;
/* 103:    */   
/* 104:    */   public synchronized int hashCode()
/* 105:    */   {
/* 106:116 */     if (this.__hashCodeCalc) {
/* 107:117 */       return 0;
/* 108:    */     }
/* 109:119 */     this.__hashCodeCalc = true;
/* 110:120 */     int _hashCode = 1;
/* 111:121 */     if (getClaveAccesoLoteConsultada() != null) {
/* 112:122 */       _hashCode += getClaveAccesoLoteConsultada().hashCode();
/* 113:    */     }
/* 114:124 */     if (getNumeroComprobantesLote() != null) {
/* 115:125 */       _hashCode += getNumeroComprobantesLote().hashCode();
/* 116:    */     }
/* 117:127 */     if (getAutorizaciones() != null) {
/* 118:128 */       for (int i = 0; i < Array.getLength(getAutorizaciones()); i++)
/* 119:    */       {
/* 120:131 */         Object obj = Array.get(getAutorizaciones(), i);
/* 121:132 */         if ((obj != null) && 
/* 122:133 */           (!obj.getClass().isArray())) {
/* 123:134 */           _hashCode += obj.hashCode();
/* 124:    */         }
/* 125:    */       }
/* 126:    */     }
/* 127:138 */     this.__hashCodeCalc = false;
/* 128:139 */     return _hashCode;
/* 129:    */   }
/* 130:    */   
/* 131:143 */   private static TypeDesc typeDesc = new TypeDesc(RespuestaLote.class, true);
/* 132:    */   
/* 133:    */   static
/* 134:    */   {
/* 135:147 */     typeDesc.setXmlType(new QName("http://ec.gob.sri.ws.autorizacion", "respuestaLote"));
/* 136:148 */     ElementDesc elemField = new ElementDesc();
/* 137:149 */     elemField.setFieldName("claveAccesoLoteConsultada");
/* 138:150 */     elemField.setXmlName(new QName("", "claveAccesoLoteConsultada"));
/* 139:151 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 140:152 */     elemField.setMinOccurs(0);
/* 141:153 */     elemField.setNillable(false);
/* 142:154 */     typeDesc.addFieldDesc(elemField);
/* 143:155 */     elemField = new ElementDesc();
/* 144:156 */     elemField.setFieldName("numeroComprobantesLote");
/* 145:157 */     elemField.setXmlName(new QName("", "numeroComprobantesLote"));
/* 146:158 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 147:159 */     elemField.setMinOccurs(0);
/* 148:160 */     elemField.setNillable(false);
/* 149:161 */     typeDesc.addFieldDesc(elemField);
/* 150:162 */     elemField = new ElementDesc();
/* 151:163 */     elemField.setFieldName("autorizaciones");
/* 152:164 */     elemField.setXmlName(new QName("", "autorizaciones"));
/* 153:165 */     elemField.setXmlType(new QName("http://ec.gob.sri.ws.autorizacion", "autorizacion"));
/* 154:166 */     elemField.setMinOccurs(0);
/* 155:167 */     elemField.setNillable(false);
/* 156:168 */     typeDesc.addFieldDesc(elemField);
/* 157:    */   }
/* 158:    */   
/* 159:    */   public static TypeDesc getTypeDesc()
/* 160:    */   {
/* 161:175 */     return typeDesc;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/* 165:    */   {
/* 166:185 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/* 167:    */   }
/* 168:    */   
/* 169:    */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/* 170:    */   {
/* 171:197 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/* 172:    */   }
/* 173:    */   
/* 174:    */   public RespuestaLote() {}
/* 175:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.gob.sri.comprobantes.ws.autorizacion.RespuestaLote
 * JD-Core Version:    0.7.0.1
 */