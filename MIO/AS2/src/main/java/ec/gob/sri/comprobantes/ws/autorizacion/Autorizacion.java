/*   1:    */ package ec.gob.sri.comprobantes.ws.autorizacion;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Calendar;
/*   5:    */ import javax.xml.namespace.QName;
/*   6:    */ import org.apache.axis.description.ElementDesc;
/*   7:    */ import org.apache.axis.description.TypeDesc;
/*   8:    */ import org.apache.axis.encoding.Deserializer;
/*   9:    */ import org.apache.axis.encoding.Serializer;
/*  10:    */ import org.apache.axis.encoding.ser.BeanDeserializer;
/*  11:    */ import org.apache.axis.encoding.ser.BeanSerializer;
/*  12:    */ 
/*  13:    */ public class Autorizacion
/*  14:    */   implements Serializable
/*  15:    */ {
/*  16:    */   private String estado;
/*  17:    */   private String numeroAutorizacion;
/*  18:    */   private Calendar fechaAutorizacion;
/*  19:    */   private String ambiente;
/*  20:    */   private String comprobante;
/*  21:    */   private AutorizacionMensajes mensajes;
/*  22:    */   
/*  23:    */   public Autorizacion(String estado, String numeroAutorizacion, Calendar fechaAutorizacion, String ambiente, String comprobante, AutorizacionMensajes mensajes)
/*  24:    */   {
/*  25: 33 */     this.estado = estado;
/*  26: 34 */     this.numeroAutorizacion = numeroAutorizacion;
/*  27: 35 */     this.fechaAutorizacion = fechaAutorizacion;
/*  28: 36 */     this.ambiente = ambiente;
/*  29: 37 */     this.comprobante = comprobante;
/*  30: 38 */     this.mensajes = mensajes;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String getEstado()
/*  34:    */   {
/*  35: 48 */     return this.estado;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void setEstado(String estado)
/*  39:    */   {
/*  40: 58 */     this.estado = estado;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String getNumeroAutorizacion()
/*  44:    */   {
/*  45: 68 */     return this.numeroAutorizacion;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void setNumeroAutorizacion(String numeroAutorizacion)
/*  49:    */   {
/*  50: 78 */     this.numeroAutorizacion = numeroAutorizacion;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Calendar getFechaAutorizacion()
/*  54:    */   {
/*  55: 88 */     return this.fechaAutorizacion;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setFechaAutorizacion(Calendar fechaAutorizacion)
/*  59:    */   {
/*  60: 98 */     this.fechaAutorizacion = fechaAutorizacion;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String getAmbiente()
/*  64:    */   {
/*  65:108 */     return this.ambiente;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setAmbiente(String ambiente)
/*  69:    */   {
/*  70:118 */     this.ambiente = ambiente;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String getComprobante()
/*  74:    */   {
/*  75:128 */     return this.comprobante;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setComprobante(String comprobante)
/*  79:    */   {
/*  80:138 */     this.comprobante = comprobante;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public AutorizacionMensajes getMensajes()
/*  84:    */   {
/*  85:148 */     return this.mensajes;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setMensajes(AutorizacionMensajes mensajes)
/*  89:    */   {
/*  90:158 */     this.mensajes = mensajes;
/*  91:    */   }
/*  92:    */   
/*  93:161 */   private Object __equalsCalc = null;
/*  94:    */   
/*  95:    */   public synchronized boolean equals(Object obj)
/*  96:    */   {
/*  97:163 */     if (!(obj instanceof Autorizacion)) {
/*  98:163 */       return false;
/*  99:    */     }
/* 100:164 */     Autorizacion other = (Autorizacion)obj;
/* 101:165 */     if (obj == null) {
/* 102:165 */       return false;
/* 103:    */     }
/* 104:166 */     if (this == obj) {
/* 105:166 */       return true;
/* 106:    */     }
/* 107:167 */     if (this.__equalsCalc != null) {
/* 108:168 */       return this.__equalsCalc == obj;
/* 109:    */     }
/* 110:170 */     this.__equalsCalc = obj;
/* 111:172 */     if ((this.estado != null) || 
/* 112:173 */       (other.getEstado() != null))
/* 113:    */     {
/* 114:173 */       if (this.estado != null) {
/* 115:175 */         if (!this.estado.equals(other.getEstado())) {}
/* 116:    */       }
/* 117:    */     }
/* 118:175 */     else if ((this.numeroAutorizacion != null) || 
/* 119:176 */       (other.getNumeroAutorizacion() != null))
/* 120:    */     {
/* 121:176 */       if (this.numeroAutorizacion != null) {
/* 122:178 */         if (!this.numeroAutorizacion.equals(other.getNumeroAutorizacion())) {}
/* 123:    */       }
/* 124:    */     }
/* 125:178 */     else if ((this.fechaAutorizacion != null) || 
/* 126:179 */       (other.getFechaAutorizacion() != null))
/* 127:    */     {
/* 128:179 */       if (this.fechaAutorizacion != null) {
/* 129:181 */         if (!this.fechaAutorizacion.equals(other.getFechaAutorizacion())) {}
/* 130:    */       }
/* 131:    */     }
/* 132:181 */     else if ((this.ambiente != null) || 
/* 133:182 */       (other.getAmbiente() != null))
/* 134:    */     {
/* 135:182 */       if (this.ambiente != null) {
/* 136:184 */         if (!this.ambiente.equals(other.getAmbiente())) {}
/* 137:    */       }
/* 138:    */     }
/* 139:184 */     else if ((this.comprobante != null) || 
/* 140:185 */       (other.getComprobante() != null))
/* 141:    */     {
/* 142:185 */       if (this.comprobante != null) {
/* 143:187 */         if (!this.comprobante.equals(other.getComprobante())) {}
/* 144:    */       }
/* 145:    */     }
/* 146:187 */     else if ((this.mensajes != null) || 
/* 147:188 */       (other.getMensajes() != null)) {
/* 148:188 */       if (this.mensajes == null) {
/* 149:    */         break label267;
/* 150:    */       }
/* 151:    */     }
/* 152:    */     label267:
/* 153:190 */     boolean _equals = this.mensajes.equals(other.getMensajes());
/* 154:191 */     this.__equalsCalc = null;
/* 155:192 */     return _equals;
/* 156:    */   }
/* 157:    */   
/* 158:195 */   private boolean __hashCodeCalc = false;
/* 159:    */   
/* 160:    */   public synchronized int hashCode()
/* 161:    */   {
/* 162:197 */     if (this.__hashCodeCalc) {
/* 163:198 */       return 0;
/* 164:    */     }
/* 165:200 */     this.__hashCodeCalc = true;
/* 166:201 */     int _hashCode = 1;
/* 167:202 */     if (getEstado() != null) {
/* 168:203 */       _hashCode += getEstado().hashCode();
/* 169:    */     }
/* 170:205 */     if (getNumeroAutorizacion() != null) {
/* 171:206 */       _hashCode += getNumeroAutorizacion().hashCode();
/* 172:    */     }
/* 173:208 */     if (getFechaAutorizacion() != null) {
/* 174:209 */       _hashCode += getFechaAutorizacion().hashCode();
/* 175:    */     }
/* 176:211 */     if (getAmbiente() != null) {
/* 177:212 */       _hashCode += getAmbiente().hashCode();
/* 178:    */     }
/* 179:214 */     if (getComprobante() != null) {
/* 180:215 */       _hashCode += getComprobante().hashCode();
/* 181:    */     }
/* 182:217 */     if (getMensajes() != null) {
/* 183:218 */       _hashCode += getMensajes().hashCode();
/* 184:    */     }
/* 185:220 */     this.__hashCodeCalc = false;
/* 186:221 */     return _hashCode;
/* 187:    */   }
/* 188:    */   
/* 189:225 */   private static TypeDesc typeDesc = new TypeDesc(Autorizacion.class, true);
/* 190:    */   
/* 191:    */   static
/* 192:    */   {
/* 193:229 */     typeDesc.setXmlType(new QName("http://ec.gob.sri.ws.autorizacion", "autorizacion"));
/* 194:230 */     ElementDesc elemField = new ElementDesc();
/* 195:231 */     elemField.setFieldName("estado");
/* 196:232 */     elemField.setXmlName(new QName("", "estado"));
/* 197:233 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 198:234 */     elemField.setMinOccurs(0);
/* 199:235 */     elemField.setNillable(false);
/* 200:236 */     typeDesc.addFieldDesc(elemField);
/* 201:237 */     elemField = new ElementDesc();
/* 202:238 */     elemField.setFieldName("numeroAutorizacion");
/* 203:239 */     elemField.setXmlName(new QName("", "numeroAutorizacion"));
/* 204:240 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 205:241 */     elemField.setMinOccurs(0);
/* 206:242 */     elemField.setNillable(false);
/* 207:243 */     typeDesc.addFieldDesc(elemField);
/* 208:244 */     elemField = new ElementDesc();
/* 209:245 */     elemField.setFieldName("fechaAutorizacion");
/* 210:246 */     elemField.setXmlName(new QName("", "fechaAutorizacion"));
/* 211:247 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
/* 212:248 */     elemField.setMinOccurs(0);
/* 213:249 */     elemField.setNillable(false);
/* 214:250 */     typeDesc.addFieldDesc(elemField);
/* 215:251 */     elemField = new ElementDesc();
/* 216:252 */     elemField.setFieldName("ambiente");
/* 217:253 */     elemField.setXmlName(new QName("", "ambiente"));
/* 218:254 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 219:255 */     elemField.setMinOccurs(0);
/* 220:256 */     elemField.setNillable(false);
/* 221:257 */     typeDesc.addFieldDesc(elemField);
/* 222:258 */     elemField = new ElementDesc();
/* 223:259 */     elemField.setFieldName("comprobante");
/* 224:260 */     elemField.setXmlName(new QName("", "comprobante"));
/* 225:261 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 226:262 */     elemField.setMinOccurs(0);
/* 227:263 */     elemField.setNillable(false);
/* 228:264 */     typeDesc.addFieldDesc(elemField);
/* 229:265 */     elemField = new ElementDesc();
/* 230:266 */     elemField.setFieldName("mensajes");
/* 231:267 */     elemField.setXmlName(new QName("", "mensajes"));
/* 232:268 */     elemField.setXmlType(new QName("http://ec.gob.sri.ws.autorizacion", ">autorizacion>mensajes"));
/* 233:269 */     elemField.setMinOccurs(0);
/* 234:270 */     elemField.setNillable(false);
/* 235:271 */     typeDesc.addFieldDesc(elemField);
/* 236:    */   }
/* 237:    */   
/* 238:    */   public static TypeDesc getTypeDesc()
/* 239:    */   {
/* 240:278 */     return typeDesc;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/* 244:    */   {
/* 245:288 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/* 246:    */   }
/* 247:    */   
/* 248:    */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/* 249:    */   {
/* 250:300 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/* 251:    */   }
/* 252:    */   
/* 253:    */   public Autorizacion() {}
/* 254:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.gob.sri.comprobantes.ws.autorizacion.Autorizacion
 * JD-Core Version:    0.7.0.1
 */