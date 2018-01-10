/*   1:    */ package ec.gob.sri.comprobantes.ws.recepcion;
/*   2:    */ 
/*   3:    */ import java.net.URL;
/*   4:    */ import java.rmi.RemoteException;
/*   5:    */ import java.util.Enumeration;
/*   6:    */ import java.util.Properties;
/*   7:    */ import java.util.Vector;
/*   8:    */ import javax.xml.namespace.QName;
/*   9:    */ import org.apache.axis.AxisFault;
/*  10:    */ import org.apache.axis.client.Call;
/*  11:    */ import org.apache.axis.client.Stub;
/*  12:    */ import org.apache.axis.constants.Style;
/*  13:    */ import org.apache.axis.constants.Use;
/*  14:    */ import org.apache.axis.description.OperationDesc;
/*  15:    */ import org.apache.axis.description.ParameterDesc;
/*  16:    */ import org.apache.axis.encoding.DeserializerFactory;
/*  17:    */ import org.apache.axis.encoding.ser.ArrayDeserializerFactory;
/*  18:    */ import org.apache.axis.encoding.ser.ArraySerializerFactory;
/*  19:    */ import org.apache.axis.encoding.ser.BeanDeserializerFactory;
/*  20:    */ import org.apache.axis.encoding.ser.BeanSerializerFactory;
/*  21:    */ import org.apache.axis.encoding.ser.EnumDeserializerFactory;
/*  22:    */ import org.apache.axis.encoding.ser.EnumSerializerFactory;
/*  23:    */ import org.apache.axis.encoding.ser.SimpleDeserializerFactory;
/*  24:    */ import org.apache.axis.encoding.ser.SimpleListDeserializerFactory;
/*  25:    */ import org.apache.axis.encoding.ser.SimpleListSerializerFactory;
/*  26:    */ import org.apache.axis.encoding.ser.SimpleSerializerFactory;
/*  27:    */ 
/*  28:    */ public class RecepcionComprobantesOfflineServiceSoapBindingStub
/*  29:    */   extends Stub
/*  30:    */   implements RecepcionComprobantesOffline
/*  31:    */ {
/*  32: 11 */   private Vector cachedSerClasses = new Vector();
/*  33: 12 */   private Vector cachedSerQNames = new Vector();
/*  34: 13 */   private Vector cachedSerFactories = new Vector();
/*  35: 14 */   private Vector cachedDeserFactories = new Vector();
/*  36: 19 */   static OperationDesc[] _operations = new OperationDesc[1];
/*  37:    */   
/*  38:    */   static
/*  39:    */   {
/*  40: 20 */     _initOperationDesc1();
/*  41:    */   }
/*  42:    */   
/*  43:    */   private static void _initOperationDesc1()
/*  44:    */   {
/*  45: 26 */     OperationDesc oper = new OperationDesc();
/*  46: 27 */     oper.setName("validarComprobante");
/*  47: 28 */     ParameterDesc param = new ParameterDesc(new QName("", "xml"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "base64Binary"), [B.class, false, false);
/*  48: 29 */     param.setOmittable(true);
/*  49: 30 */     oper.addParameter(param);
/*  50: 31 */     oper.setReturnType(new QName("http://ec.gob.sri.ws.recepcion", "respuestaSolicitud"));
/*  51: 32 */     oper.setReturnClass(RespuestaSolicitud.class);
/*  52: 33 */     oper.setReturnQName(new QName("", "RespuestaRecepcionComprobante"));
/*  53: 34 */     oper.setStyle(Style.WRAPPED);
/*  54: 35 */     oper.setUse(Use.LITERAL);
/*  55: 36 */     _operations[0] = oper;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public RecepcionComprobantesOfflineServiceSoapBindingStub()
/*  59:    */     throws AxisFault
/*  60:    */   {
/*  61: 41 */     this(null);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public RecepcionComprobantesOfflineServiceSoapBindingStub(URL endpointURL, javax.xml.rpc.Service service)
/*  65:    */     throws AxisFault
/*  66:    */   {
/*  67: 45 */     this(service);
/*  68: 46 */     this.cachedEndpoint = endpointURL;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public RecepcionComprobantesOfflineServiceSoapBindingStub(javax.xml.rpc.Service service)
/*  72:    */     throws AxisFault
/*  73:    */   {
/*  74: 50 */     if (service == null) {
/*  75: 51 */       this.service = new org.apache.axis.client.Service();
/*  76:    */     } else {
/*  77: 53 */       this.service = service;
/*  78:    */     }
/*  79: 55 */     ((org.apache.axis.client.Service)this.service).setTypeMappingVersion("1.2");
/*  80:    */     
/*  81:    */ 
/*  82:    */ 
/*  83: 59 */     Class beansf = BeanSerializerFactory.class;
/*  84: 60 */     Class beandf = BeanDeserializerFactory.class;
/*  85: 61 */     Class enumsf = EnumSerializerFactory.class;
/*  86: 62 */     Class enumdf = EnumDeserializerFactory.class;
/*  87: 63 */     Class arraysf = ArraySerializerFactory.class;
/*  88: 64 */     Class arraydf = ArrayDeserializerFactory.class;
/*  89: 65 */     Class simplesf = SimpleSerializerFactory.class;
/*  90: 66 */     Class simpledf = SimpleDeserializerFactory.class;
/*  91: 67 */     Class simplelistsf = SimpleListSerializerFactory.class;
/*  92: 68 */     Class simplelistdf = SimpleListDeserializerFactory.class;
/*  93: 69 */     QName qName = new QName("http://ec.gob.sri.ws.recepcion", ">comprobante>mensajes");
/*  94: 70 */     this.cachedSerQNames.add(qName);
/*  95: 71 */     Class cls = ComprobanteMensajes.class;
/*  96: 72 */     this.cachedSerClasses.add(cls);
/*  97: 73 */     this.cachedSerFactories.add(beansf);
/*  98: 74 */     this.cachedDeserFactories.add(beandf);
/*  99:    */     
/* 100: 76 */     qName = new QName("http://ec.gob.sri.ws.recepcion", ">respuestaSolicitud>comprobantes");
/* 101: 77 */     this.cachedSerQNames.add(qName);
/* 102: 78 */     cls = RespuestaSolicitudComprobantes.class;
/* 103: 79 */     this.cachedSerClasses.add(cls);
/* 104: 80 */     this.cachedSerFactories.add(beansf);
/* 105: 81 */     this.cachedDeserFactories.add(beandf);
/* 106:    */     
/* 107: 83 */     qName = new QName("http://ec.gob.sri.ws.recepcion", "comprobante");
/* 108: 84 */     this.cachedSerQNames.add(qName);
/* 109: 85 */     cls = Comprobante.class;
/* 110: 86 */     this.cachedSerClasses.add(cls);
/* 111: 87 */     this.cachedSerFactories.add(beansf);
/* 112: 88 */     this.cachedDeserFactories.add(beandf);
/* 113:    */     
/* 114: 90 */     qName = new QName("http://ec.gob.sri.ws.recepcion", "mensaje");
/* 115: 91 */     this.cachedSerQNames.add(qName);
/* 116: 92 */     cls = Mensaje.class;
/* 117: 93 */     this.cachedSerClasses.add(cls);
/* 118: 94 */     this.cachedSerFactories.add(beansf);
/* 119: 95 */     this.cachedDeserFactories.add(beandf);
/* 120:    */     
/* 121: 97 */     qName = new QName("http://ec.gob.sri.ws.recepcion", "respuestaSolicitud");
/* 122: 98 */     this.cachedSerQNames.add(qName);
/* 123: 99 */     cls = RespuestaSolicitud.class;
/* 124:100 */     this.cachedSerClasses.add(cls);
/* 125:101 */     this.cachedSerFactories.add(beansf);
/* 126:102 */     this.cachedDeserFactories.add(beandf);
/* 127:    */   }
/* 128:    */   
/* 129:    */   protected Call createCall()
/* 130:    */     throws RemoteException
/* 131:    */   {
/* 132:    */     try
/* 133:    */     {
/* 134:108 */       Call _call = super._createCall();
/* 135:109 */       if (this.maintainSessionSet) {
/* 136:110 */         _call.setMaintainSession(this.maintainSession);
/* 137:    */       }
/* 138:112 */       if (this.cachedUsername != null) {
/* 139:113 */         _call.setUsername(this.cachedUsername);
/* 140:    */       }
/* 141:115 */       if (this.cachedPassword != null) {
/* 142:116 */         _call.setPassword(this.cachedPassword);
/* 143:    */       }
/* 144:118 */       if (this.cachedEndpoint != null) {
/* 145:119 */         _call.setTargetEndpointAddress(this.cachedEndpoint);
/* 146:    */       }
/* 147:121 */       if (this.cachedTimeout != null) {
/* 148:122 */         _call.setTimeout(this.cachedTimeout);
/* 149:    */       }
/* 150:124 */       if (this.cachedPortName != null) {
/* 151:125 */         _call.setPortName(this.cachedPortName);
/* 152:    */       }
/* 153:127 */       Enumeration keys = this.cachedProperties.keys();
/* 154:128 */       while (keys.hasMoreElements())
/* 155:    */       {
/* 156:129 */         String key = (String)keys.nextElement();
/* 157:130 */         _call.setProperty(key, this.cachedProperties.get(key));
/* 158:    */       }
/* 159:137 */       synchronized (this)
/* 160:    */       {
/* 161:138 */         if (firstCall())
/* 162:    */         {
/* 163:140 */           _call.setEncodingStyle(null);
/* 164:141 */           for (int i = 0; i < this.cachedSerFactories.size(); i++)
/* 165:    */           {
/* 166:142 */             Class cls = (Class)this.cachedSerClasses.get(i);
/* 167:    */             
/* 168:144 */             QName qName = (QName)this.cachedSerQNames.get(i);
/* 169:145 */             Object x = this.cachedSerFactories.get(i);
/* 170:146 */             if ((x instanceof Class))
/* 171:    */             {
/* 172:148 */               Class sf = (Class)this.cachedSerFactories.get(i);
/* 173:    */               
/* 174:150 */               Class df = (Class)this.cachedDeserFactories.get(i);
/* 175:151 */               _call.registerTypeMapping(cls, qName, sf, df, false);
/* 176:    */             }
/* 177:153 */             else if ((x instanceof javax.xml.rpc.encoding.SerializerFactory))
/* 178:    */             {
/* 179:155 */               org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)this.cachedSerFactories.get(i);
/* 180:    */               
/* 181:157 */               DeserializerFactory df = (DeserializerFactory)this.cachedDeserFactories.get(i);
/* 182:158 */               _call.registerTypeMapping(cls, qName, sf, df, false);
/* 183:    */             }
/* 184:    */           }
/* 185:    */         }
/* 186:    */       }
/* 187:163 */       return _call;
/* 188:    */     }
/* 189:    */     catch (Throwable _t)
/* 190:    */     {
/* 191:166 */       throw new AxisFault("Failure trying to get the Call object", _t);
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   /* Error */
/* 196:    */   public RespuestaSolicitud validarComprobante(byte[] xml)
/* 197:    */     throws RemoteException
/* 198:    */   {
/* 199:    */     // Byte code:
/* 200:    */     //   0: aload_0
/* 201:    */     //   1: getfield 29	org/apache/axis/client/Stub:cachedEndpoint	Ljava/net/URL;
/* 202:    */     //   4: ifnonnull +11 -> 15
/* 203:    */     //   7: new 95	org/apache/axis/NoEndPointException
/* 204:    */     //   10: dup
/* 205:    */     //   11: invokespecial 96	org/apache/axis/NoEndPointException:<init>	()V
/* 206:    */     //   14: athrow
/* 207:    */     //   15: aload_0
/* 208:    */     //   16: invokevirtual 97	ec/gob/sri/comprobantes/ws/recepcion/RecepcionComprobantesOfflineServiceSoapBindingStub:createCall	()Lorg/apache/axis/client/Call;
/* 209:    */     //   19: astore_2
/* 210:    */     //   20: aload_2
/* 211:    */     //   21: getstatic 27	ec/gob/sri/comprobantes/ws/recepcion/RecepcionComprobantesOfflineServiceSoapBindingStub:_operations	[Lorg/apache/axis/description/OperationDesc;
/* 212:    */     //   24: iconst_0
/* 213:    */     //   25: aaload
/* 214:    */     //   26: invokevirtual 98	org/apache/axis/client/Call:setOperation	(Lorg/apache/axis/description/OperationDesc;)V
/* 215:    */     //   29: aload_2
/* 216:    */     //   30: iconst_1
/* 217:    */     //   31: invokevirtual 99	org/apache/axis/client/Call:setUseSOAPAction	(Z)V
/* 218:    */     //   34: aload_2
/* 219:    */     //   35: ldc 7
/* 220:    */     //   37: invokevirtual 100	org/apache/axis/client/Call:setSOAPActionURI	(Ljava/lang/String;)V
/* 221:    */     //   40: aload_2
/* 222:    */     //   41: aconst_null
/* 223:    */     //   42: invokevirtual 82	org/apache/axis/client/Call:setEncodingStyle	(Ljava/lang/String;)V
/* 224:    */     //   45: aload_2
/* 225:    */     //   46: ldc 102
/* 226:    */     //   48: getstatic 103	java/lang/Boolean:FALSE	Ljava/lang/Boolean;
/* 227:    */     //   51: invokevirtual 80	org/apache/axis/client/Call:setProperty	(Ljava/lang/String;Ljava/lang/Object;)V
/* 228:    */     //   54: aload_2
/* 229:    */     //   55: ldc 105
/* 230:    */     //   57: getstatic 103	java/lang/Boolean:FALSE	Ljava/lang/Boolean;
/* 231:    */     //   60: invokevirtual 80	org/apache/axis/client/Call:setProperty	(Ljava/lang/String;Ljava/lang/Object;)V
/* 232:    */     //   63: aload_2
/* 233:    */     //   64: getstatic 106	org/apache/axis/soap/SOAPConstants:SOAP11_CONSTANTS	Lorg/apache/axis/soap/SOAP11Constants;
/* 234:    */     //   67: invokevirtual 107	org/apache/axis/client/Call:setSOAPVersion	(Lorg/apache/axis/soap/SOAPConstants;)V
/* 235:    */     //   70: aload_2
/* 236:    */     //   71: new 6	javax/xml/namespace/QName
/* 237:    */     //   74: dup
/* 238:    */     //   75: ldc 16
/* 239:    */     //   77: ldc 3
/* 240:    */     //   79: invokespecial 9	javax/xml/namespace/QName:<init>	(Ljava/lang/String;Ljava/lang/String;)V
/* 241:    */     //   82: invokevirtual 108	org/apache/axis/client/Call:setOperationName	(Ljavax/xml/namespace/QName;)V
/* 242:    */     //   85: aload_0
/* 243:    */     //   86: aload_2
/* 244:    */     //   87: invokevirtual 109	ec/gob/sri/comprobantes/ws/recepcion/RecepcionComprobantesOfflineServiceSoapBindingStub:setRequestHeaders	(Lorg/apache/axis/client/Call;)V
/* 245:    */     //   90: aload_0
/* 246:    */     //   91: aload_2
/* 247:    */     //   92: invokevirtual 110	ec/gob/sri/comprobantes/ws/recepcion/RecepcionComprobantesOfflineServiceSoapBindingStub:setAttachments	(Lorg/apache/axis/client/Call;)V
/* 248:    */     //   95: aload_2
/* 249:    */     //   96: iconst_1
/* 250:    */     //   97: anewarray 111	java/lang/Object
/* 251:    */     //   100: dup
/* 252:    */     //   101: iconst_0
/* 253:    */     //   102: aload_1
/* 254:    */     //   103: aastore
/* 255:    */     //   104: invokevirtual 112	org/apache/axis/client/Call:invoke	([Ljava/lang/Object;)Ljava/lang/Object;
/* 256:    */     //   107: astore_3
/* 257:    */     //   108: aload_3
/* 258:    */     //   109: instanceof 113
/* 259:    */     //   112: ifeq +8 -> 120
/* 260:    */     //   115: aload_3
/* 261:    */     //   116: checkcast 113	java/rmi/RemoteException
/* 262:    */     //   119: athrow
/* 263:    */     //   120: aload_0
/* 264:    */     //   121: aload_2
/* 265:    */     //   122: invokevirtual 114	ec/gob/sri/comprobantes/ws/recepcion/RecepcionComprobantesOfflineServiceSoapBindingStub:extractAttachments	(Lorg/apache/axis/client/Call;)V
/* 266:    */     //   125: aload_3
/* 267:    */     //   126: checkcast 19	ec/gob/sri/comprobantes/ws/recepcion/RespuestaSolicitud
/* 268:    */     //   129: areturn
/* 269:    */     //   130: astore 4
/* 270:    */     //   132: aload_3
/* 271:    */     //   133: ldc 19
/* 272:    */     //   135: invokestatic 116	org/apache/axis/utils/JavaUtils:convert	(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
/* 273:    */     //   138: checkcast 19	ec/gob/sri/comprobantes/ws/recepcion/RespuestaSolicitud
/* 274:    */     //   141: areturn
/* 275:    */     //   142: astore_3
/* 276:    */     //   143: aload_3
/* 277:    */     //   144: athrow
/* 278:    */     // Line number table:
/* 279:    */     //   Java source line #171	-> byte code offset #0
/* 280:    */     //   Java source line #172	-> byte code offset #7
/* 281:    */     //   Java source line #174	-> byte code offset #15
/* 282:    */     //   Java source line #175	-> byte code offset #20
/* 283:    */     //   Java source line #176	-> byte code offset #29
/* 284:    */     //   Java source line #177	-> byte code offset #34
/* 285:    */     //   Java source line #178	-> byte code offset #40
/* 286:    */     //   Java source line #179	-> byte code offset #45
/* 287:    */     //   Java source line #180	-> byte code offset #54
/* 288:    */     //   Java source line #181	-> byte code offset #63
/* 289:    */     //   Java source line #182	-> byte code offset #70
/* 290:    */     //   Java source line #184	-> byte code offset #85
/* 291:    */     //   Java source line #185	-> byte code offset #90
/* 292:    */     //   Java source line #186	-> byte code offset #95
/* 293:    */     //   Java source line #188	-> byte code offset #108
/* 294:    */     //   Java source line #189	-> byte code offset #115
/* 295:    */     //   Java source line #192	-> byte code offset #120
/* 296:    */     //   Java source line #194	-> byte code offset #125
/* 297:    */     //   Java source line #195	-> byte code offset #130
/* 298:    */     //   Java source line #196	-> byte code offset #132
/* 299:    */     //   Java source line #199	-> byte code offset #142
/* 300:    */     //   Java source line #200	-> byte code offset #143
/* 301:    */     // Local variable table:
/* 302:    */     //   start	length	slot	name	signature
/* 303:    */     //   0	145	0	this	RecepcionComprobantesOfflineServiceSoapBindingStub
/* 304:    */     //   0	145	1	xml	byte[]
/* 305:    */     //   19	103	2	_call	Call
/* 306:    */     //   107	26	3	_resp	Object
/* 307:    */     //   142	2	3	axisFaultException	AxisFault
/* 308:    */     //   130	3	4	_exception	java.lang.Exception
/* 309:    */     // Exception table:
/* 310:    */     //   from	to	target	type
/* 311:    */     //   125	129	130	java/lang/Exception
/* 312:    */     //   95	129	142	org/apache/axis/AxisFault
/* 313:    */     //   130	141	142	org/apache/axis/AxisFault
/* 314:    */   }
/* 315:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.gob.sri.comprobantes.ws.recepcion.RecepcionComprobantesOfflineServiceSoapBindingStub
 * JD-Core Version:    0.7.0.1
 */