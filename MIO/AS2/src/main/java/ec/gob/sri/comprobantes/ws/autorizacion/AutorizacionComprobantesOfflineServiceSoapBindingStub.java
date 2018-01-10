/*   1:    */ package ec.gob.sri.comprobantes.ws.autorizacion;
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
/*  28:    */ public class AutorizacionComprobantesOfflineServiceSoapBindingStub
/*  29:    */   extends Stub
/*  30:    */   implements AutorizacionComprobantesOffline
/*  31:    */ {
/*  32: 11 */   private Vector cachedSerClasses = new Vector();
/*  33: 12 */   private Vector cachedSerQNames = new Vector();
/*  34: 13 */   private Vector cachedSerFactories = new Vector();
/*  35: 14 */   private Vector cachedDeserFactories = new Vector();
/*  36: 19 */   static OperationDesc[] _operations = new OperationDesc[2];
/*  37:    */   
/*  38:    */   static
/*  39:    */   {
/*  40: 20 */     _initOperationDesc1();
/*  41:    */   }
/*  42:    */   
/*  43:    */   private static void _initOperationDesc1()
/*  44:    */   {
/*  45: 26 */     OperationDesc oper = new OperationDesc();
/*  46: 27 */     oper.setName("autorizacionComprobante");
/*  47: 28 */     ParameterDesc param = new ParameterDesc(new QName("", "claveAccesoComprobante"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  48: 29 */     param.setOmittable(true);
/*  49: 30 */     oper.addParameter(param);
/*  50: 31 */     oper.setReturnType(new QName("http://ec.gob.sri.ws.autorizacion", "respuestaComprobante"));
/*  51: 32 */     oper.setReturnClass(RespuestaComprobante.class);
/*  52: 33 */     oper.setReturnQName(new QName("", "RespuestaAutorizacionComprobante"));
/*  53: 34 */     oper.setStyle(Style.WRAPPED);
/*  54: 35 */     oper.setUse(Use.LITERAL);
/*  55: 36 */     _operations[0] = oper;
/*  56:    */     
/*  57: 38 */     oper = new OperationDesc();
/*  58: 39 */     oper.setName("autorizacionComprobanteLote");
/*  59: 40 */     param = new ParameterDesc(new QName("", "claveAccesoLote"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  60: 41 */     param.setOmittable(true);
/*  61: 42 */     oper.addParameter(param);
/*  62: 43 */     oper.setReturnType(new QName("http://ec.gob.sri.ws.autorizacion", "respuestaLote"));
/*  63: 44 */     oper.setReturnClass(RespuestaLote.class);
/*  64: 45 */     oper.setReturnQName(new QName("", "RespuestaAutorizacionLote"));
/*  65: 46 */     oper.setStyle(Style.WRAPPED);
/*  66: 47 */     oper.setUse(Use.LITERAL);
/*  67: 48 */     _operations[1] = oper;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public AutorizacionComprobantesOfflineServiceSoapBindingStub()
/*  71:    */     throws AxisFault
/*  72:    */   {
/*  73: 53 */     this(null);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public AutorizacionComprobantesOfflineServiceSoapBindingStub(URL endpointURL, javax.xml.rpc.Service service)
/*  77:    */     throws AxisFault
/*  78:    */   {
/*  79: 57 */     this(service);
/*  80: 58 */     this.cachedEndpoint = endpointURL;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public AutorizacionComprobantesOfflineServiceSoapBindingStub(javax.xml.rpc.Service service)
/*  84:    */     throws AxisFault
/*  85:    */   {
/*  86: 62 */     if (service == null) {
/*  87: 63 */       this.service = new org.apache.axis.client.Service();
/*  88:    */     } else {
/*  89: 65 */       this.service = service;
/*  90:    */     }
/*  91: 67 */     ((org.apache.axis.client.Service)this.service).setTypeMappingVersion("1.2");
/*  92:    */     
/*  93:    */ 
/*  94:    */ 
/*  95: 71 */     Class beansf = BeanSerializerFactory.class;
/*  96: 72 */     Class beandf = BeanDeserializerFactory.class;
/*  97: 73 */     Class enumsf = EnumSerializerFactory.class;
/*  98: 74 */     Class enumdf = EnumDeserializerFactory.class;
/*  99: 75 */     Class arraysf = ArraySerializerFactory.class;
/* 100: 76 */     Class arraydf = ArrayDeserializerFactory.class;
/* 101: 77 */     Class simplesf = SimpleSerializerFactory.class;
/* 102: 78 */     Class simpledf = SimpleDeserializerFactory.class;
/* 103: 79 */     Class simplelistsf = SimpleListSerializerFactory.class;
/* 104: 80 */     Class simplelistdf = SimpleListDeserializerFactory.class;
/* 105: 81 */     QName qName = new QName("http://ec.gob.sri.ws.autorizacion", ">autorizacion>mensajes");
/* 106: 82 */     this.cachedSerQNames.add(qName);
/* 107: 83 */     Class cls = AutorizacionMensajes.class;
/* 108: 84 */     this.cachedSerClasses.add(cls);
/* 109: 85 */     this.cachedSerFactories.add(beansf);
/* 110: 86 */     this.cachedDeserFactories.add(beandf);
/* 111:    */     
/* 112: 88 */     qName = new QName("http://ec.gob.sri.ws.autorizacion", ">respuestaComprobante>autorizaciones");
/* 113: 89 */     this.cachedSerQNames.add(qName);
/* 114: 90 */     cls = RespuestaComprobanteAutorizaciones.class;
/* 115: 91 */     this.cachedSerClasses.add(cls);
/* 116: 92 */     this.cachedSerFactories.add(beansf);
/* 117: 93 */     this.cachedDeserFactories.add(beandf);
/* 118:    */     
/* 119: 95 */     qName = new QName("http://ec.gob.sri.ws.autorizacion", ">respuestaLote>autorizaciones");
/* 120: 96 */     this.cachedSerQNames.add(qName);
/* 121: 97 */     cls = RespuestaLoteAutorizaciones.class;
/* 122: 98 */     this.cachedSerClasses.add(cls);
/* 123: 99 */     this.cachedSerFactories.add(beansf);
/* 124:100 */     this.cachedDeserFactories.add(beandf);
/* 125:    */     
/* 126:102 */     qName = new QName("http://ec.gob.sri.ws.autorizacion", "autorizacion");
/* 127:103 */     this.cachedSerQNames.add(qName);
/* 128:104 */     cls = Autorizacion.class;
/* 129:105 */     this.cachedSerClasses.add(cls);
/* 130:106 */     this.cachedSerFactories.add(beansf);
/* 131:107 */     this.cachedDeserFactories.add(beandf);
/* 132:    */     
/* 133:109 */     qName = new QName("http://ec.gob.sri.ws.autorizacion", "mensaje");
/* 134:110 */     this.cachedSerQNames.add(qName);
/* 135:111 */     cls = Mensaje.class;
/* 136:112 */     this.cachedSerClasses.add(cls);
/* 137:113 */     this.cachedSerFactories.add(beansf);
/* 138:114 */     this.cachedDeserFactories.add(beandf);
/* 139:    */     
/* 140:116 */     qName = new QName("http://ec.gob.sri.ws.autorizacion", "respuestaComprobante");
/* 141:117 */     this.cachedSerQNames.add(qName);
/* 142:118 */     cls = RespuestaComprobante.class;
/* 143:119 */     this.cachedSerClasses.add(cls);
/* 144:120 */     this.cachedSerFactories.add(beansf);
/* 145:121 */     this.cachedDeserFactories.add(beandf);
/* 146:    */     
/* 147:123 */     qName = new QName("http://ec.gob.sri.ws.autorizacion", "respuestaLote");
/* 148:124 */     this.cachedSerQNames.add(qName);
/* 149:125 */     cls = RespuestaLote.class;
/* 150:126 */     this.cachedSerClasses.add(cls);
/* 151:127 */     this.cachedSerFactories.add(beansf);
/* 152:128 */     this.cachedDeserFactories.add(beandf);
/* 153:    */   }
/* 154:    */   
/* 155:    */   protected Call createCall()
/* 156:    */     throws RemoteException
/* 157:    */   {
/* 158:    */     try
/* 159:    */     {
/* 160:134 */       Call _call = super._createCall();
/* 161:135 */       if (this.maintainSessionSet) {
/* 162:136 */         _call.setMaintainSession(this.maintainSession);
/* 163:    */       }
/* 164:138 */       if (this.cachedUsername != null) {
/* 165:139 */         _call.setUsername(this.cachedUsername);
/* 166:    */       }
/* 167:141 */       if (this.cachedPassword != null) {
/* 168:142 */         _call.setPassword(this.cachedPassword);
/* 169:    */       }
/* 170:144 */       if (this.cachedEndpoint != null) {
/* 171:145 */         _call.setTargetEndpointAddress(this.cachedEndpoint);
/* 172:    */       }
/* 173:147 */       if (this.cachedTimeout != null) {
/* 174:148 */         _call.setTimeout(this.cachedTimeout);
/* 175:    */       }
/* 176:150 */       if (this.cachedPortName != null) {
/* 177:151 */         _call.setPortName(this.cachedPortName);
/* 178:    */       }
/* 179:153 */       Enumeration keys = this.cachedProperties.keys();
/* 180:154 */       while (keys.hasMoreElements())
/* 181:    */       {
/* 182:155 */         String key = (String)keys.nextElement();
/* 183:156 */         _call.setProperty(key, this.cachedProperties.get(key));
/* 184:    */       }
/* 185:163 */       synchronized (this)
/* 186:    */       {
/* 187:164 */         if (firstCall())
/* 188:    */         {
/* 189:166 */           _call.setEncodingStyle(null);
/* 190:167 */           for (int i = 0; i < this.cachedSerFactories.size(); i++)
/* 191:    */           {
/* 192:168 */             Class cls = (Class)this.cachedSerClasses.get(i);
/* 193:    */             
/* 194:170 */             QName qName = (QName)this.cachedSerQNames.get(i);
/* 195:171 */             Object x = this.cachedSerFactories.get(i);
/* 196:172 */             if ((x instanceof Class))
/* 197:    */             {
/* 198:174 */               Class sf = (Class)this.cachedSerFactories.get(i);
/* 199:    */               
/* 200:176 */               Class df = (Class)this.cachedDeserFactories.get(i);
/* 201:177 */               _call.registerTypeMapping(cls, qName, sf, df, false);
/* 202:    */             }
/* 203:179 */             else if ((x instanceof javax.xml.rpc.encoding.SerializerFactory))
/* 204:    */             {
/* 205:181 */               org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)this.cachedSerFactories.get(i);
/* 206:    */               
/* 207:183 */               DeserializerFactory df = (DeserializerFactory)this.cachedDeserFactories.get(i);
/* 208:184 */               _call.registerTypeMapping(cls, qName, sf, df, false);
/* 209:    */             }
/* 210:    */           }
/* 211:    */         }
/* 212:    */       }
/* 213:189 */       return _call;
/* 214:    */     }
/* 215:    */     catch (Throwable _t)
/* 216:    */     {
/* 217:192 */       throw new AxisFault("Failure trying to get the Call object", _t);
/* 218:    */     }
/* 219:    */   }
/* 220:    */   
/* 221:    */   /* Error */
/* 222:    */   public RespuestaComprobante autorizacionComprobante(String claveAccesoComprobante)
/* 223:    */     throws RemoteException
/* 224:    */   {
/* 225:    */     // Byte code:
/* 226:    */     //   0: aload_0
/* 227:    */     //   1: getfield 34	org/apache/axis/client/Stub:cachedEndpoint	Ljava/net/URL;
/* 228:    */     //   4: ifnonnull +11 -> 15
/* 229:    */     //   7: new 101	org/apache/axis/NoEndPointException
/* 230:    */     //   10: dup
/* 231:    */     //   11: invokespecial 102	org/apache/axis/NoEndPointException:<init>	()V
/* 232:    */     //   14: athrow
/* 233:    */     //   15: aload_0
/* 234:    */     //   16: invokevirtual 103	ec/gob/sri/comprobantes/ws/autorizacion/AutorizacionComprobantesOfflineServiceSoapBindingStub:createCall	()Lorg/apache/axis/client/Call;
/* 235:    */     //   19: astore_2
/* 236:    */     //   20: aload_2
/* 237:    */     //   21: getstatic 27	ec/gob/sri/comprobantes/ws/autorizacion/AutorizacionComprobantesOfflineServiceSoapBindingStub:_operations	[Lorg/apache/axis/description/OperationDesc;
/* 238:    */     //   24: iconst_0
/* 239:    */     //   25: aaload
/* 240:    */     //   26: invokevirtual 104	org/apache/axis/client/Call:setOperation	(Lorg/apache/axis/description/OperationDesc;)V
/* 241:    */     //   29: aload_2
/* 242:    */     //   30: iconst_1
/* 243:    */     //   31: invokevirtual 105	org/apache/axis/client/Call:setUseSOAPAction	(Z)V
/* 244:    */     //   34: aload_2
/* 245:    */     //   35: ldc 7
/* 246:    */     //   37: invokevirtual 106	org/apache/axis/client/Call:setSOAPActionURI	(Ljava/lang/String;)V
/* 247:    */     //   40: aload_2
/* 248:    */     //   41: aconst_null
/* 249:    */     //   42: invokevirtual 88	org/apache/axis/client/Call:setEncodingStyle	(Ljava/lang/String;)V
/* 250:    */     //   45: aload_2
/* 251:    */     //   46: ldc 108
/* 252:    */     //   48: getstatic 109	java/lang/Boolean:FALSE	Ljava/lang/Boolean;
/* 253:    */     //   51: invokevirtual 86	org/apache/axis/client/Call:setProperty	(Ljava/lang/String;Ljava/lang/Object;)V
/* 254:    */     //   54: aload_2
/* 255:    */     //   55: ldc 111
/* 256:    */     //   57: getstatic 109	java/lang/Boolean:FALSE	Ljava/lang/Boolean;
/* 257:    */     //   60: invokevirtual 86	org/apache/axis/client/Call:setProperty	(Ljava/lang/String;Ljava/lang/Object;)V
/* 258:    */     //   63: aload_2
/* 259:    */     //   64: getstatic 112	org/apache/axis/soap/SOAPConstants:SOAP11_CONSTANTS	Lorg/apache/axis/soap/SOAP11Constants;
/* 260:    */     //   67: invokevirtual 113	org/apache/axis/client/Call:setSOAPVersion	(Lorg/apache/axis/soap/SOAPConstants;)V
/* 261:    */     //   70: aload_2
/* 262:    */     //   71: new 6	javax/xml/namespace/QName
/* 263:    */     //   74: dup
/* 264:    */     //   75: ldc 16
/* 265:    */     //   77: ldc 3
/* 266:    */     //   79: invokespecial 9	javax/xml/namespace/QName:<init>	(Ljava/lang/String;Ljava/lang/String;)V
/* 267:    */     //   82: invokevirtual 114	org/apache/axis/client/Call:setOperationName	(Ljavax/xml/namespace/QName;)V
/* 268:    */     //   85: aload_0
/* 269:    */     //   86: aload_2
/* 270:    */     //   87: invokevirtual 115	ec/gob/sri/comprobantes/ws/autorizacion/AutorizacionComprobantesOfflineServiceSoapBindingStub:setRequestHeaders	(Lorg/apache/axis/client/Call;)V
/* 271:    */     //   90: aload_0
/* 272:    */     //   91: aload_2
/* 273:    */     //   92: invokevirtual 116	ec/gob/sri/comprobantes/ws/autorizacion/AutorizacionComprobantesOfflineServiceSoapBindingStub:setAttachments	(Lorg/apache/axis/client/Call;)V
/* 274:    */     //   95: aload_2
/* 275:    */     //   96: iconst_1
/* 276:    */     //   97: anewarray 117	java/lang/Object
/* 277:    */     //   100: dup
/* 278:    */     //   101: iconst_0
/* 279:    */     //   102: aload_1
/* 280:    */     //   103: aastore
/* 281:    */     //   104: invokevirtual 118	org/apache/axis/client/Call:invoke	([Ljava/lang/Object;)Ljava/lang/Object;
/* 282:    */     //   107: astore_3
/* 283:    */     //   108: aload_3
/* 284:    */     //   109: instanceof 119
/* 285:    */     //   112: ifeq +8 -> 120
/* 286:    */     //   115: aload_3
/* 287:    */     //   116: checkcast 119	java/rmi/RemoteException
/* 288:    */     //   119: athrow
/* 289:    */     //   120: aload_0
/* 290:    */     //   121: aload_2
/* 291:    */     //   122: invokevirtual 120	ec/gob/sri/comprobantes/ws/autorizacion/AutorizacionComprobantesOfflineServiceSoapBindingStub:extractAttachments	(Lorg/apache/axis/client/Call;)V
/* 292:    */     //   125: aload_3
/* 293:    */     //   126: checkcast 19	ec/gob/sri/comprobantes/ws/autorizacion/RespuestaComprobante
/* 294:    */     //   129: areturn
/* 295:    */     //   130: astore 4
/* 296:    */     //   132: aload_3
/* 297:    */     //   133: ldc 19
/* 298:    */     //   135: invokestatic 122	org/apache/axis/utils/JavaUtils:convert	(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
/* 299:    */     //   138: checkcast 19	ec/gob/sri/comprobantes/ws/autorizacion/RespuestaComprobante
/* 300:    */     //   141: areturn
/* 301:    */     //   142: astore_3
/* 302:    */     //   143: aload_3
/* 303:    */     //   144: athrow
/* 304:    */     // Line number table:
/* 305:    */     //   Java source line #197	-> byte code offset #0
/* 306:    */     //   Java source line #198	-> byte code offset #7
/* 307:    */     //   Java source line #200	-> byte code offset #15
/* 308:    */     //   Java source line #201	-> byte code offset #20
/* 309:    */     //   Java source line #202	-> byte code offset #29
/* 310:    */     //   Java source line #203	-> byte code offset #34
/* 311:    */     //   Java source line #204	-> byte code offset #40
/* 312:    */     //   Java source line #205	-> byte code offset #45
/* 313:    */     //   Java source line #206	-> byte code offset #54
/* 314:    */     //   Java source line #207	-> byte code offset #63
/* 315:    */     //   Java source line #208	-> byte code offset #70
/* 316:    */     //   Java source line #210	-> byte code offset #85
/* 317:    */     //   Java source line #211	-> byte code offset #90
/* 318:    */     //   Java source line #212	-> byte code offset #95
/* 319:    */     //   Java source line #214	-> byte code offset #108
/* 320:    */     //   Java source line #215	-> byte code offset #115
/* 321:    */     //   Java source line #218	-> byte code offset #120
/* 322:    */     //   Java source line #220	-> byte code offset #125
/* 323:    */     //   Java source line #221	-> byte code offset #130
/* 324:    */     //   Java source line #222	-> byte code offset #132
/* 325:    */     //   Java source line #225	-> byte code offset #142
/* 326:    */     //   Java source line #226	-> byte code offset #143
/* 327:    */     // Local variable table:
/* 328:    */     //   start	length	slot	name	signature
/* 329:    */     //   0	145	0	this	AutorizacionComprobantesOfflineServiceSoapBindingStub
/* 330:    */     //   0	145	1	claveAccesoComprobante	String
/* 331:    */     //   19	103	2	_call	Call
/* 332:    */     //   107	26	3	_resp	Object
/* 333:    */     //   142	2	3	axisFaultException	AxisFault
/* 334:    */     //   130	3	4	_exception	java.lang.Exception
/* 335:    */     // Exception table:
/* 336:    */     //   from	to	target	type
/* 337:    */     //   125	129	130	java/lang/Exception
/* 338:    */     //   95	129	142	org/apache/axis/AxisFault
/* 339:    */     //   130	141	142	org/apache/axis/AxisFault
/* 340:    */   }
/* 341:    */   
/* 342:    */   /* Error */
/* 343:    */   public RespuestaLote autorizacionComprobanteLote(String claveAccesoLote)
/* 344:    */     throws RemoteException
/* 345:    */   {
/* 346:    */     // Byte code:
/* 347:    */     //   0: aload_0
/* 348:    */     //   1: getfield 34	org/apache/axis/client/Stub:cachedEndpoint	Ljava/net/URL;
/* 349:    */     //   4: ifnonnull +11 -> 15
/* 350:    */     //   7: new 101	org/apache/axis/NoEndPointException
/* 351:    */     //   10: dup
/* 352:    */     //   11: invokespecial 102	org/apache/axis/NoEndPointException:<init>	()V
/* 353:    */     //   14: athrow
/* 354:    */     //   15: aload_0
/* 355:    */     //   16: invokevirtual 103	ec/gob/sri/comprobantes/ws/autorizacion/AutorizacionComprobantesOfflineServiceSoapBindingStub:createCall	()Lorg/apache/axis/client/Call;
/* 356:    */     //   19: astore_2
/* 357:    */     //   20: aload_2
/* 358:    */     //   21: getstatic 27	ec/gob/sri/comprobantes/ws/autorizacion/AutorizacionComprobantesOfflineServiceSoapBindingStub:_operations	[Lorg/apache/axis/description/OperationDesc;
/* 359:    */     //   24: iconst_1
/* 360:    */     //   25: aaload
/* 361:    */     //   26: invokevirtual 104	org/apache/axis/client/Call:setOperation	(Lorg/apache/axis/description/OperationDesc;)V
/* 362:    */     //   29: aload_2
/* 363:    */     //   30: iconst_1
/* 364:    */     //   31: invokevirtual 105	org/apache/axis/client/Call:setUseSOAPAction	(Z)V
/* 365:    */     //   34: aload_2
/* 366:    */     //   35: ldc 7
/* 367:    */     //   37: invokevirtual 106	org/apache/axis/client/Call:setSOAPActionURI	(Ljava/lang/String;)V
/* 368:    */     //   40: aload_2
/* 369:    */     //   41: aconst_null
/* 370:    */     //   42: invokevirtual 88	org/apache/axis/client/Call:setEncodingStyle	(Ljava/lang/String;)V
/* 371:    */     //   45: aload_2
/* 372:    */     //   46: ldc 108
/* 373:    */     //   48: getstatic 109	java/lang/Boolean:FALSE	Ljava/lang/Boolean;
/* 374:    */     //   51: invokevirtual 86	org/apache/axis/client/Call:setProperty	(Ljava/lang/String;Ljava/lang/Object;)V
/* 375:    */     //   54: aload_2
/* 376:    */     //   55: ldc 111
/* 377:    */     //   57: getstatic 109	java/lang/Boolean:FALSE	Ljava/lang/Boolean;
/* 378:    */     //   60: invokevirtual 86	org/apache/axis/client/Call:setProperty	(Ljava/lang/String;Ljava/lang/Object;)V
/* 379:    */     //   63: aload_2
/* 380:    */     //   64: getstatic 112	org/apache/axis/soap/SOAPConstants:SOAP11_CONSTANTS	Lorg/apache/axis/soap/SOAP11Constants;
/* 381:    */     //   67: invokevirtual 113	org/apache/axis/client/Call:setSOAPVersion	(Lorg/apache/axis/soap/SOAPConstants;)V
/* 382:    */     //   70: aload_2
/* 383:    */     //   71: new 6	javax/xml/namespace/QName
/* 384:    */     //   74: dup
/* 385:    */     //   75: ldc 16
/* 386:    */     //   77: ldc 28
/* 387:    */     //   79: invokespecial 9	javax/xml/namespace/QName:<init>	(Ljava/lang/String;Ljava/lang/String;)V
/* 388:    */     //   82: invokevirtual 114	org/apache/axis/client/Call:setOperationName	(Ljavax/xml/namespace/QName;)V
/* 389:    */     //   85: aload_0
/* 390:    */     //   86: aload_2
/* 391:    */     //   87: invokevirtual 115	ec/gob/sri/comprobantes/ws/autorizacion/AutorizacionComprobantesOfflineServiceSoapBindingStub:setRequestHeaders	(Lorg/apache/axis/client/Call;)V
/* 392:    */     //   90: aload_0
/* 393:    */     //   91: aload_2
/* 394:    */     //   92: invokevirtual 116	ec/gob/sri/comprobantes/ws/autorizacion/AutorizacionComprobantesOfflineServiceSoapBindingStub:setAttachments	(Lorg/apache/axis/client/Call;)V
/* 395:    */     //   95: aload_2
/* 396:    */     //   96: iconst_1
/* 397:    */     //   97: anewarray 117	java/lang/Object
/* 398:    */     //   100: dup
/* 399:    */     //   101: iconst_0
/* 400:    */     //   102: aload_1
/* 401:    */     //   103: aastore
/* 402:    */     //   104: invokevirtual 118	org/apache/axis/client/Call:invoke	([Ljava/lang/Object;)Ljava/lang/Object;
/* 403:    */     //   107: astore_3
/* 404:    */     //   108: aload_3
/* 405:    */     //   109: instanceof 119
/* 406:    */     //   112: ifeq +8 -> 120
/* 407:    */     //   115: aload_3
/* 408:    */     //   116: checkcast 119	java/rmi/RemoteException
/* 409:    */     //   119: athrow
/* 410:    */     //   120: aload_0
/* 411:    */     //   121: aload_2
/* 412:    */     //   122: invokevirtual 120	ec/gob/sri/comprobantes/ws/autorizacion/AutorizacionComprobantesOfflineServiceSoapBindingStub:extractAttachments	(Lorg/apache/axis/client/Call;)V
/* 413:    */     //   125: aload_3
/* 414:    */     //   126: checkcast 31	ec/gob/sri/comprobantes/ws/autorizacion/RespuestaLote
/* 415:    */     //   129: areturn
/* 416:    */     //   130: astore 4
/* 417:    */     //   132: aload_3
/* 418:    */     //   133: ldc 31
/* 419:    */     //   135: invokestatic 122	org/apache/axis/utils/JavaUtils:convert	(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
/* 420:    */     //   138: checkcast 31	ec/gob/sri/comprobantes/ws/autorizacion/RespuestaLote
/* 421:    */     //   141: areturn
/* 422:    */     //   142: astore_3
/* 423:    */     //   143: aload_3
/* 424:    */     //   144: athrow
/* 425:    */     // Line number table:
/* 426:    */     //   Java source line #231	-> byte code offset #0
/* 427:    */     //   Java source line #232	-> byte code offset #7
/* 428:    */     //   Java source line #234	-> byte code offset #15
/* 429:    */     //   Java source line #235	-> byte code offset #20
/* 430:    */     //   Java source line #236	-> byte code offset #29
/* 431:    */     //   Java source line #237	-> byte code offset #34
/* 432:    */     //   Java source line #238	-> byte code offset #40
/* 433:    */     //   Java source line #239	-> byte code offset #45
/* 434:    */     //   Java source line #240	-> byte code offset #54
/* 435:    */     //   Java source line #241	-> byte code offset #63
/* 436:    */     //   Java source line #242	-> byte code offset #70
/* 437:    */     //   Java source line #244	-> byte code offset #85
/* 438:    */     //   Java source line #245	-> byte code offset #90
/* 439:    */     //   Java source line #246	-> byte code offset #95
/* 440:    */     //   Java source line #248	-> byte code offset #108
/* 441:    */     //   Java source line #249	-> byte code offset #115
/* 442:    */     //   Java source line #252	-> byte code offset #120
/* 443:    */     //   Java source line #254	-> byte code offset #125
/* 444:    */     //   Java source line #255	-> byte code offset #130
/* 445:    */     //   Java source line #256	-> byte code offset #132
/* 446:    */     //   Java source line #259	-> byte code offset #142
/* 447:    */     //   Java source line #260	-> byte code offset #143
/* 448:    */     // Local variable table:
/* 449:    */     //   start	length	slot	name	signature
/* 450:    */     //   0	145	0	this	AutorizacionComprobantesOfflineServiceSoapBindingStub
/* 451:    */     //   0	145	1	claveAccesoLote	String
/* 452:    */     //   19	103	2	_call	Call
/* 453:    */     //   107	26	3	_resp	Object
/* 454:    */     //   142	2	3	axisFaultException	AxisFault
/* 455:    */     //   130	3	4	_exception	java.lang.Exception
/* 456:    */     // Exception table:
/* 457:    */     //   from	to	target	type
/* 458:    */     //   125	129	130	java/lang/Exception
/* 459:    */     //   95	129	142	org/apache/axis/AxisFault
/* 460:    */     //   130	141	142	org/apache/axis/AxisFault
/* 461:    */   }
/* 462:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.gob.sri.comprobantes.ws.autorizacion.AutorizacionComprobantesOfflineServiceSoapBindingStub
 * JD-Core Version:    0.7.0.1
 */