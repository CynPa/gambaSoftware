/*   1:    */ package ec.gob.sri.comprobantes.ws.recepcion;
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
/*  14:    */ public class ComprobanteMensajes
/*  15:    */   implements Serializable
/*  16:    */ {
/*  17:    */   private Mensaje[] mensaje;
/*  18:    */   
/*  19:    */   public ComprobanteMensajes(Mensaje[] mensaje)
/*  20:    */   {
/*  21: 17 */     this.mensaje = mensaje;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Mensaje[] getMensaje()
/*  25:    */   {
/*  26: 26 */     return this.mensaje;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setMensaje(Mensaje[] mensaje)
/*  30:    */   {
/*  31: 35 */     this.mensaje = mensaje;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Mensaje getMensaje(int i)
/*  35:    */   {
/*  36: 39 */     return this.mensaje[i];
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setMensaje(int i, Mensaje _value)
/*  40:    */   {
/*  41: 43 */     this.mensaje[i] = _value;
/*  42:    */   }
/*  43:    */   
/*  44: 46 */   private Object __equalsCalc = null;
/*  45:    */   
/*  46:    */   public synchronized boolean equals(Object obj)
/*  47:    */   {
/*  48: 49 */     if (!(obj instanceof ComprobanteMensajes)) {
/*  49: 50 */       return false;
/*  50:    */     }
/*  51: 51 */     ComprobanteMensajes other = (ComprobanteMensajes)obj;
/*  52: 52 */     if (obj == null) {
/*  53: 53 */       return false;
/*  54:    */     }
/*  55: 54 */     if (this == obj) {
/*  56: 55 */       return true;
/*  57:    */     }
/*  58: 56 */     if (this.__equalsCalc != null) {
/*  59: 57 */       return this.__equalsCalc == obj;
/*  60:    */     }
/*  61: 59 */     this.__equalsCalc = obj;
/*  62:    */     
/*  63: 61 */     boolean _equals = ((this.mensaje == null) && (other.getMensaje() == null)) || ((this.mensaje != null) && (Arrays.equals(this.mensaje, other
/*  64: 62 */       .getMensaje())));
/*  65: 63 */     this.__equalsCalc = null;
/*  66: 64 */     return _equals;
/*  67:    */   }
/*  68:    */   
/*  69: 67 */   private boolean __hashCodeCalc = false;
/*  70:    */   
/*  71:    */   public synchronized int hashCode()
/*  72:    */   {
/*  73: 70 */     if (this.__hashCodeCalc) {
/*  74: 71 */       return 0;
/*  75:    */     }
/*  76: 73 */     this.__hashCodeCalc = true;
/*  77: 74 */     int _hashCode = 1;
/*  78: 75 */     if (getMensaje() != null) {
/*  79: 76 */       for (int i = 0; i < Array.getLength(getMensaje()); i++)
/*  80:    */       {
/*  81: 77 */         Object obj = Array.get(getMensaje(), i);
/*  82: 78 */         if ((obj != null) && (!obj.getClass().isArray())) {
/*  83: 79 */           _hashCode += obj.hashCode();
/*  84:    */         }
/*  85:    */       }
/*  86:    */     }
/*  87: 83 */     this.__hashCodeCalc = false;
/*  88: 84 */     return _hashCode;
/*  89:    */   }
/*  90:    */   
/*  91: 88 */   private static TypeDesc typeDesc = new TypeDesc(ComprobanteMensajes.class, true);
/*  92:    */   
/*  93:    */   static
/*  94:    */   {
/*  95: 91 */     typeDesc.setXmlType(new QName("http://ec.gob.sri.ws.recepcion", ">comprobante>mensajes"));
/*  96: 92 */     ElementDesc elemField = new ElementDesc();
/*  97: 93 */     elemField.setFieldName("mensaje");
/*  98: 94 */     elemField.setXmlName(new QName("http://ec.gob.sri.ws.recepcion", "mensaje"));
/*  99: 95 */     elemField.setXmlType(new QName("http://ec.gob.sri.ws.recepcion", "mensaje"));
/* 100: 96 */     elemField.setMinOccurs(0);
/* 101: 97 */     elemField.setNillable(false);
/* 102: 98 */     elemField.setMaxOccursUnbounded(true);
/* 103: 99 */     typeDesc.addFieldDesc(elemField);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static TypeDesc getTypeDesc()
/* 107:    */   {
/* 108:106 */     return typeDesc;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/* 112:    */   {
/* 113:114 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/* 117:    */   {
/* 118:122 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public ComprobanteMensajes() {}
/* 122:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.gob.sri.comprobantes.ws.recepcion.ComprobanteMensajes
 * JD-Core Version:    0.7.0.1
 */