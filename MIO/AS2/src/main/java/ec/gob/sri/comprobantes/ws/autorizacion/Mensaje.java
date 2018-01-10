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
/*  12:    */ public class Mensaje
/*  13:    */   implements Serializable
/*  14:    */ {
/*  15:    */   private String identificador;
/*  16:    */   private String mensaje;
/*  17:    */   private String informacionAdicional;
/*  18:    */   private String tipo;
/*  19:    */   
/*  20:    */   public Mensaje(String identificador, String mensaje, String informacionAdicional, String tipo)
/*  21:    */   {
/*  22: 24 */     this.identificador = identificador;
/*  23: 25 */     this.mensaje = mensaje;
/*  24: 26 */     this.informacionAdicional = informacionAdicional;
/*  25: 27 */     this.tipo = tipo;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String getIdentificador()
/*  29:    */   {
/*  30: 37 */     return this.identificador;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void setIdentificador(String identificador)
/*  34:    */   {
/*  35: 47 */     this.identificador = identificador;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String getMensaje()
/*  39:    */   {
/*  40: 57 */     return this.mensaje;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setMensaje(String mensaje)
/*  44:    */   {
/*  45: 67 */     this.mensaje = mensaje;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String getInformacionAdicional()
/*  49:    */   {
/*  50: 77 */     return this.informacionAdicional;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setInformacionAdicional(String informacionAdicional)
/*  54:    */   {
/*  55: 87 */     this.informacionAdicional = informacionAdicional;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getTipo()
/*  59:    */   {
/*  60: 97 */     return this.tipo;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setTipo(String tipo)
/*  64:    */   {
/*  65:107 */     this.tipo = tipo;
/*  66:    */   }
/*  67:    */   
/*  68:110 */   private Object __equalsCalc = null;
/*  69:    */   
/*  70:    */   public synchronized boolean equals(Object obj)
/*  71:    */   {
/*  72:112 */     if (!(obj instanceof Mensaje)) {
/*  73:112 */       return false;
/*  74:    */     }
/*  75:113 */     Mensaje other = (Mensaje)obj;
/*  76:114 */     if (obj == null) {
/*  77:114 */       return false;
/*  78:    */     }
/*  79:115 */     if (this == obj) {
/*  80:115 */       return true;
/*  81:    */     }
/*  82:116 */     if (this.__equalsCalc != null) {
/*  83:117 */       return this.__equalsCalc == obj;
/*  84:    */     }
/*  85:119 */     this.__equalsCalc = obj;
/*  86:121 */     if ((this.identificador != null) || 
/*  87:122 */       (other.getIdentificador() != null))
/*  88:    */     {
/*  89:122 */       if (this.identificador != null) {
/*  90:124 */         if (!this.identificador.equals(other.getIdentificador())) {}
/*  91:    */       }
/*  92:    */     }
/*  93:124 */     else if ((this.mensaje != null) || 
/*  94:125 */       (other.getMensaje() != null))
/*  95:    */     {
/*  96:125 */       if (this.mensaje != null) {
/*  97:127 */         if (!this.mensaje.equals(other.getMensaje())) {}
/*  98:    */       }
/*  99:    */     }
/* 100:127 */     else if ((this.informacionAdicional != null) || 
/* 101:128 */       (other.getInformacionAdicional() != null))
/* 102:    */     {
/* 103:128 */       if (this.informacionAdicional != null) {
/* 104:130 */         if (!this.informacionAdicional.equals(other.getInformacionAdicional())) {}
/* 105:    */       }
/* 106:    */     }
/* 107:130 */     else if ((this.tipo != null) || 
/* 108:131 */       (other.getTipo() != null)) {
/* 109:131 */       if (this.tipo == null) {
/* 110:    */         break label197;
/* 111:    */       }
/* 112:    */     }
/* 113:    */     label197:
/* 114:133 */     boolean _equals = this.tipo.equals(other.getTipo());
/* 115:134 */     this.__equalsCalc = null;
/* 116:135 */     return _equals;
/* 117:    */   }
/* 118:    */   
/* 119:138 */   private boolean __hashCodeCalc = false;
/* 120:    */   
/* 121:    */   public synchronized int hashCode()
/* 122:    */   {
/* 123:140 */     if (this.__hashCodeCalc) {
/* 124:141 */       return 0;
/* 125:    */     }
/* 126:143 */     this.__hashCodeCalc = true;
/* 127:144 */     int _hashCode = 1;
/* 128:145 */     if (getIdentificador() != null) {
/* 129:146 */       _hashCode += getIdentificador().hashCode();
/* 130:    */     }
/* 131:148 */     if (getMensaje() != null) {
/* 132:149 */       _hashCode += getMensaje().hashCode();
/* 133:    */     }
/* 134:151 */     if (getInformacionAdicional() != null) {
/* 135:152 */       _hashCode += getInformacionAdicional().hashCode();
/* 136:    */     }
/* 137:154 */     if (getTipo() != null) {
/* 138:155 */       _hashCode += getTipo().hashCode();
/* 139:    */     }
/* 140:157 */     this.__hashCodeCalc = false;
/* 141:158 */     return _hashCode;
/* 142:    */   }
/* 143:    */   
/* 144:162 */   private static TypeDesc typeDesc = new TypeDesc(Mensaje.class, true);
/* 145:    */   
/* 146:    */   static
/* 147:    */   {
/* 148:166 */     typeDesc.setXmlType(new QName("http://ec.gob.sri.ws.autorizacion", "mensaje"));
/* 149:167 */     ElementDesc elemField = new ElementDesc();
/* 150:168 */     elemField.setFieldName("identificador");
/* 151:169 */     elemField.setXmlName(new QName("", "identificador"));
/* 152:170 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 153:171 */     elemField.setMinOccurs(0);
/* 154:172 */     elemField.setNillable(false);
/* 155:173 */     typeDesc.addFieldDesc(elemField);
/* 156:174 */     elemField = new ElementDesc();
/* 157:175 */     elemField.setFieldName("mensaje");
/* 158:176 */     elemField.setXmlName(new QName("", "mensaje"));
/* 159:177 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 160:178 */     elemField.setMinOccurs(0);
/* 161:179 */     elemField.setNillable(false);
/* 162:180 */     typeDesc.addFieldDesc(elemField);
/* 163:181 */     elemField = new ElementDesc();
/* 164:182 */     elemField.setFieldName("informacionAdicional");
/* 165:183 */     elemField.setXmlName(new QName("", "informacionAdicional"));
/* 166:184 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 167:185 */     elemField.setMinOccurs(0);
/* 168:186 */     elemField.setNillable(false);
/* 169:187 */     typeDesc.addFieldDesc(elemField);
/* 170:188 */     elemField = new ElementDesc();
/* 171:189 */     elemField.setFieldName("tipo");
/* 172:190 */     elemField.setXmlName(new QName("", "tipo"));
/* 173:191 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 174:192 */     elemField.setMinOccurs(0);
/* 175:193 */     elemField.setNillable(false);
/* 176:194 */     typeDesc.addFieldDesc(elemField);
/* 177:    */   }
/* 178:    */   
/* 179:    */   public static TypeDesc getTypeDesc()
/* 180:    */   {
/* 181:201 */     return typeDesc;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/* 185:    */   {
/* 186:211 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/* 187:    */   }
/* 188:    */   
/* 189:    */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/* 190:    */   {
/* 191:223 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/* 192:    */   }
/* 193:    */   
/* 194:    */   public Mensaje() {}
/* 195:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.gob.sri.comprobantes.ws.autorizacion.Mensaje
 * JD-Core Version:    0.7.0.1
 */