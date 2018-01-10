/*   1:    */ package ec.com.facturae.ws.comprobante;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.lang.reflect.Array;
/*   6:    */ import java.util.Arrays;
/*   7:    */ import javax.xml.namespace.QName;
/*   8:    */ import org.apache.axis.AxisFault;
/*   9:    */ import org.apache.axis.description.ElementDesc;
/*  10:    */ import org.apache.axis.description.TypeDesc;
/*  11:    */ import org.apache.axis.encoding.Deserializer;
/*  12:    */ import org.apache.axis.encoding.SerializationContext;
/*  13:    */ import org.apache.axis.encoding.Serializer;
/*  14:    */ import org.apache.axis.encoding.ser.BeanDeserializer;
/*  15:    */ import org.apache.axis.encoding.ser.BeanSerializer;
/*  16:    */ 
/*  17:    */ public class FacturaeException
/*  18:    */   extends AxisFault
/*  19:    */   implements Serializable
/*  20:    */ {
/*  21:    */   private String codigoExcepcion;
/*  22:    */   private String mensaje;
/*  23:    */   private String[] mensajes;
/*  24:    */   private String[] codigoMensajes;
/*  25:    */   private String message1;
/*  26:    */   
/*  27:    */   public FacturaeException(String codigoExcepcion, String mensaje, String[] mensajes, String[] codigoMensajes, String message1)
/*  28:    */   {
/*  29: 30 */     this.codigoExcepcion = codigoExcepcion;
/*  30: 31 */     this.mensaje = mensaje;
/*  31: 32 */     this.mensajes = mensajes;
/*  32: 33 */     this.codigoMensajes = codigoMensajes;
/*  33: 34 */     this.message1 = message1;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String getCodigoExcepcion()
/*  37:    */   {
/*  38: 44 */     return this.codigoExcepcion;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setCodigoExcepcion(String codigoExcepcion)
/*  42:    */   {
/*  43: 54 */     this.codigoExcepcion = codigoExcepcion;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getMensaje()
/*  47:    */   {
/*  48: 64 */     return this.mensaje;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setMensaje(String mensaje)
/*  52:    */   {
/*  53: 74 */     this.mensaje = mensaje;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String[] getMensajes()
/*  57:    */   {
/*  58: 84 */     return this.mensajes;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setMensajes(String[] mensajes)
/*  62:    */   {
/*  63: 94 */     this.mensajes = mensajes;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String getMensajes(int i)
/*  67:    */   {
/*  68: 98 */     return this.mensajes[i];
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setMensajes(int i, String _value)
/*  72:    */   {
/*  73:102 */     this.mensajes[i] = _value;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String[] getCodigoMensajes()
/*  77:    */   {
/*  78:112 */     return this.codigoMensajes;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setCodigoMensajes(String[] codigoMensajes)
/*  82:    */   {
/*  83:122 */     this.codigoMensajes = codigoMensajes;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getCodigoMensajes(int i)
/*  87:    */   {
/*  88:126 */     return this.codigoMensajes[i];
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setCodigoMensajes(int i, String _value)
/*  92:    */   {
/*  93:130 */     this.codigoMensajes[i] = _value;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getMessage1()
/*  97:    */   {
/*  98:140 */     return this.message1;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setMessage1(String message1)
/* 102:    */   {
/* 103:150 */     this.message1 = message1;
/* 104:    */   }
/* 105:    */   
/* 106:153 */   private Object __equalsCalc = null;
/* 107:    */   
/* 108:    */   public synchronized boolean equals(Object obj)
/* 109:    */   {
/* 110:155 */     if (!(obj instanceof FacturaeException)) {
/* 111:155 */       return false;
/* 112:    */     }
/* 113:156 */     FacturaeException other = (FacturaeException)obj;
/* 114:157 */     if (obj == null) {
/* 115:157 */       return false;
/* 116:    */     }
/* 117:158 */     if (this == obj) {
/* 118:158 */       return true;
/* 119:    */     }
/* 120:159 */     if (this.__equalsCalc != null) {
/* 121:160 */       return this.__equalsCalc == obj;
/* 122:    */     }
/* 123:162 */     this.__equalsCalc = obj;
/* 124:164 */     if ((this.codigoExcepcion != null) || 
/* 125:165 */       (other.getCodigoExcepcion() != null))
/* 126:    */     {
/* 127:165 */       if (this.codigoExcepcion != null) {
/* 128:167 */         if (!this.codigoExcepcion.equals(other.getCodigoExcepcion())) {}
/* 129:    */       }
/* 130:    */     }
/* 131:167 */     else if ((this.mensaje != null) || 
/* 132:168 */       (other.getMensaje() != null))
/* 133:    */     {
/* 134:168 */       if (this.mensaje != null) {
/* 135:170 */         if (!this.mensaje.equals(other.getMensaje())) {}
/* 136:    */       }
/* 137:    */     }
/* 138:170 */     else if ((this.mensajes != null) || 
/* 139:171 */       (other.getMensajes() != null))
/* 140:    */     {
/* 141:171 */       if (this.mensajes != null) {
/* 142:173 */         if (!Arrays.equals(this.mensajes, other.getMensajes())) {}
/* 143:    */       }
/* 144:    */     }
/* 145:173 */     else if ((this.codigoMensajes != null) || 
/* 146:174 */       (other.getCodigoMensajes() != null))
/* 147:    */     {
/* 148:174 */       if (this.codigoMensajes != null) {
/* 149:176 */         if (!Arrays.equals(this.codigoMensajes, other.getCodigoMensajes())) {}
/* 150:    */       }
/* 151:    */     }
/* 152:176 */     else if ((this.message1 != null) || 
/* 153:177 */       (other.getMessage1() != null)) {
/* 154:177 */       if (this.message1 == null) {
/* 155:    */         break label232;
/* 156:    */       }
/* 157:    */     }
/* 158:    */     label232:
/* 159:179 */     boolean _equals = this.message1.equals(other.getMessage1());
/* 160:180 */     this.__equalsCalc = null;
/* 161:181 */     return _equals;
/* 162:    */   }
/* 163:    */   
/* 164:184 */   private boolean __hashCodeCalc = false;
/* 165:    */   
/* 166:    */   public synchronized int hashCode()
/* 167:    */   {
/* 168:186 */     if (this.__hashCodeCalc) {
/* 169:187 */       return 0;
/* 170:    */     }
/* 171:189 */     this.__hashCodeCalc = true;
/* 172:190 */     int _hashCode = 1;
/* 173:191 */     if (getCodigoExcepcion() != null) {
/* 174:192 */       _hashCode += getCodigoExcepcion().hashCode();
/* 175:    */     }
/* 176:194 */     if (getMensaje() != null) {
/* 177:195 */       _hashCode += getMensaje().hashCode();
/* 178:    */     }
/* 179:197 */     if (getMensajes() != null) {
/* 180:198 */       for (int i = 0; i < Array.getLength(getMensajes()); i++)
/* 181:    */       {
/* 182:201 */         Object obj = Array.get(getMensajes(), i);
/* 183:202 */         if ((obj != null) && 
/* 184:203 */           (!obj.getClass().isArray())) {
/* 185:204 */           _hashCode += obj.hashCode();
/* 186:    */         }
/* 187:    */       }
/* 188:    */     }
/* 189:208 */     if (getCodigoMensajes() != null) {
/* 190:209 */       for (int i = 0; i < Array.getLength(getCodigoMensajes()); i++)
/* 191:    */       {
/* 192:212 */         Object obj = Array.get(getCodigoMensajes(), i);
/* 193:213 */         if ((obj != null) && 
/* 194:214 */           (!obj.getClass().isArray())) {
/* 195:215 */           _hashCode += obj.hashCode();
/* 196:    */         }
/* 197:    */       }
/* 198:    */     }
/* 199:219 */     if (getMessage1() != null) {
/* 200:220 */       _hashCode += getMessage1().hashCode();
/* 201:    */     }
/* 202:222 */     this.__hashCodeCalc = false;
/* 203:223 */     return _hashCode;
/* 204:    */   }
/* 205:    */   
/* 206:227 */   private static TypeDesc typeDesc = new TypeDesc(FacturaeException.class, true);
/* 207:    */   
/* 208:    */   static
/* 209:    */   {
/* 210:231 */     typeDesc.setXmlType(new QName("http://soap.asinfo.com.ec/", "FacturaeException"));
/* 211:232 */     ElementDesc elemField = new ElementDesc();
/* 212:233 */     elemField.setFieldName("codigoExcepcion");
/* 213:234 */     elemField.setXmlName(new QName("", "codigoExcepcion"));
/* 214:235 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 215:236 */     elemField.setMinOccurs(0);
/* 216:237 */     elemField.setNillable(false);
/* 217:238 */     typeDesc.addFieldDesc(elemField);
/* 218:239 */     elemField = new ElementDesc();
/* 219:240 */     elemField.setFieldName("mensaje");
/* 220:241 */     elemField.setXmlName(new QName("", "mensaje"));
/* 221:242 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 222:243 */     elemField.setMinOccurs(0);
/* 223:244 */     elemField.setNillable(false);
/* 224:245 */     typeDesc.addFieldDesc(elemField);
/* 225:246 */     elemField = new ElementDesc();
/* 226:247 */     elemField.setFieldName("mensajes");
/* 227:248 */     elemField.setXmlName(new QName("", "mensajes"));
/* 228:249 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 229:250 */     elemField.setMinOccurs(0);
/* 230:251 */     elemField.setNillable(false);
/* 231:252 */     elemField.setMaxOccursUnbounded(true);
/* 232:253 */     typeDesc.addFieldDesc(elemField);
/* 233:254 */     elemField = new ElementDesc();
/* 234:255 */     elemField.setFieldName("codigoMensajes");
/* 235:256 */     elemField.setXmlName(new QName("", "codigoMensajes"));
/* 236:257 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 237:258 */     elemField.setMinOccurs(0);
/* 238:259 */     elemField.setNillable(false);
/* 239:260 */     elemField.setMaxOccursUnbounded(true);
/* 240:261 */     typeDesc.addFieldDesc(elemField);
/* 241:262 */     elemField = new ElementDesc();
/* 242:263 */     elemField.setFieldName("message1");
/* 243:264 */     elemField.setXmlName(new QName("", "message"));
/* 244:265 */     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
/* 245:266 */     elemField.setMinOccurs(0);
/* 246:267 */     elemField.setNillable(false);
/* 247:268 */     typeDesc.addFieldDesc(elemField);
/* 248:    */   }
/* 249:    */   
/* 250:    */   public static TypeDesc getTypeDesc()
/* 251:    */   {
/* 252:275 */     return typeDesc;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/* 256:    */   {
/* 257:285 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/* 258:    */   }
/* 259:    */   
/* 260:    */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/* 261:    */   {
/* 262:297 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void writeDetails(QName qname, SerializationContext context)
/* 266:    */     throws IOException
/* 267:    */   {
/* 268:307 */     context.serialize(qname, null, this);
/* 269:    */   }
/* 270:    */   
/* 271:    */   public FacturaeException() {}
/* 272:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.com.facturae.ws.comprobante.FacturaeException
 * JD-Core Version:    0.7.0.1
 */