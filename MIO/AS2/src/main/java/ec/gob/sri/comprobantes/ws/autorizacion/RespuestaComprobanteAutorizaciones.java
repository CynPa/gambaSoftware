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
/*  14:    */ public class RespuestaComprobanteAutorizaciones
/*  15:    */   implements Serializable
/*  16:    */ {
/*  17:    */   private Autorizacion[] autorizacion;
/*  18:    */   
/*  19:    */   public RespuestaComprobanteAutorizaciones(Autorizacion[] autorizacion)
/*  20:    */   {
/*  21: 18 */     this.autorizacion = autorizacion;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public Autorizacion[] getAutorizacion()
/*  25:    */   {
/*  26: 28 */     return this.autorizacion;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setAutorizacion(Autorizacion[] autorizacion)
/*  30:    */   {
/*  31: 38 */     this.autorizacion = autorizacion;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Autorizacion getAutorizacion(int i)
/*  35:    */   {
/*  36: 42 */     return this.autorizacion[i];
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setAutorizacion(int i, Autorizacion _value)
/*  40:    */   {
/*  41: 46 */     this.autorizacion[i] = _value;
/*  42:    */   }
/*  43:    */   
/*  44: 49 */   private Object __equalsCalc = null;
/*  45:    */   
/*  46:    */   public synchronized boolean equals(Object obj)
/*  47:    */   {
/*  48: 51 */     if (!(obj instanceof RespuestaComprobanteAutorizaciones)) {
/*  49: 51 */       return false;
/*  50:    */     }
/*  51: 52 */     RespuestaComprobanteAutorizaciones other = (RespuestaComprobanteAutorizaciones)obj;
/*  52: 53 */     if (obj == null) {
/*  53: 53 */       return false;
/*  54:    */     }
/*  55: 54 */     if (this == obj) {
/*  56: 54 */       return true;
/*  57:    */     }
/*  58: 55 */     if (this.__equalsCalc != null) {
/*  59: 56 */       return this.__equalsCalc == obj;
/*  60:    */     }
/*  61: 58 */     this.__equalsCalc = obj;
/*  62: 60 */     if ((this.autorizacion != null) || 
/*  63: 61 */       (other.getAutorizacion() != null)) {
/*  64: 61 */       if (this.autorizacion == null) {
/*  65:    */         break label92;
/*  66:    */       }
/*  67:    */     }
/*  68:    */     label92:
/*  69: 63 */     boolean _equals = Arrays.equals(this.autorizacion, other.getAutorizacion());
/*  70: 64 */     this.__equalsCalc = null;
/*  71: 65 */     return _equals;
/*  72:    */   }
/*  73:    */   
/*  74: 68 */   private boolean __hashCodeCalc = false;
/*  75:    */   
/*  76:    */   public synchronized int hashCode()
/*  77:    */   {
/*  78: 70 */     if (this.__hashCodeCalc) {
/*  79: 71 */       return 0;
/*  80:    */     }
/*  81: 73 */     this.__hashCodeCalc = true;
/*  82: 74 */     int _hashCode = 1;
/*  83: 75 */     if (getAutorizacion() != null) {
/*  84: 76 */       for (int i = 0; i < Array.getLength(getAutorizacion()); i++)
/*  85:    */       {
/*  86: 79 */         Object obj = Array.get(getAutorizacion(), i);
/*  87: 80 */         if ((obj != null) && 
/*  88: 81 */           (!obj.getClass().isArray())) {
/*  89: 82 */           _hashCode += obj.hashCode();
/*  90:    */         }
/*  91:    */       }
/*  92:    */     }
/*  93: 86 */     this.__hashCodeCalc = false;
/*  94: 87 */     return _hashCode;
/*  95:    */   }
/*  96:    */   
/*  97: 91 */   private static TypeDesc typeDesc = new TypeDesc(RespuestaComprobanteAutorizaciones.class, true);
/*  98:    */   
/*  99:    */   static
/* 100:    */   {
/* 101: 95 */     typeDesc.setXmlType(new QName("http://ec.gob.sri.ws.autorizacion", ">respuestaComprobante>autorizaciones"));
/* 102: 96 */     ElementDesc elemField = new ElementDesc();
/* 103: 97 */     elemField.setFieldName("autorizacion");
/* 104: 98 */     elemField.setXmlName(new QName("http://ec.gob.sri.ws.autorizacion", "autorizacion"));
/* 105: 99 */     elemField.setXmlType(new QName("http://ec.gob.sri.ws.autorizacion", "autorizacion"));
/* 106:100 */     elemField.setMinOccurs(0);
/* 107:101 */     elemField.setNillable(false);
/* 108:102 */     elemField.setMaxOccursUnbounded(true);
/* 109:103 */     typeDesc.addFieldDesc(elemField);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static TypeDesc getTypeDesc()
/* 113:    */   {
/* 114:110 */     return typeDesc;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
/* 118:    */   {
/* 119:120 */     return new BeanSerializer(_javaType, _xmlType, typeDesc);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
/* 123:    */   {
/* 124:132 */     return new BeanDeserializer(_javaType, _xmlType, typeDesc);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public RespuestaComprobanteAutorizaciones() {}
/* 128:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.gob.sri.comprobantes.ws.autorizacion.RespuestaComprobanteAutorizaciones
 * JD-Core Version:    0.7.0.1
 */