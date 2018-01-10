/*   1:    */ package ec.com.facturae.ws.comprobante.impl;
/*   2:    */ 
/*   3:    */ import ec.com.facturae.ws.comprobante.FacturaeException;
/*   4:    */ import ec.com.facturae.ws.comprobante.ServicioWebComprobante;
/*   5:    */ import java.net.URL;
/*   6:    */ import java.rmi.RemoteException;
/*   7:    */ import java.util.Enumeration;
/*   8:    */ import java.util.Properties;
/*   9:    */ import java.util.Vector;
/*  10:    */ import javax.xml.namespace.QName;
/*  11:    */ import org.apache.axis.AxisFault;
/*  12:    */ import org.apache.axis.client.Call;
/*  13:    */ import org.apache.axis.client.Stub;
/*  14:    */ import org.apache.axis.constants.Style;
/*  15:    */ import org.apache.axis.constants.Use;
/*  16:    */ import org.apache.axis.description.FaultDesc;
/*  17:    */ import org.apache.axis.description.OperationDesc;
/*  18:    */ import org.apache.axis.description.ParameterDesc;
/*  19:    */ import org.apache.axis.encoding.DeserializerFactory;
/*  20:    */ import org.apache.axis.encoding.ser.ArrayDeserializerFactory;
/*  21:    */ import org.apache.axis.encoding.ser.ArraySerializerFactory;
/*  22:    */ import org.apache.axis.encoding.ser.BeanDeserializerFactory;
/*  23:    */ import org.apache.axis.encoding.ser.BeanSerializerFactory;
/*  24:    */ import org.apache.axis.encoding.ser.EnumDeserializerFactory;
/*  25:    */ import org.apache.axis.encoding.ser.EnumSerializerFactory;
/*  26:    */ import org.apache.axis.encoding.ser.SimpleDeserializerFactory;
/*  27:    */ import org.apache.axis.encoding.ser.SimpleListDeserializerFactory;
/*  28:    */ import org.apache.axis.encoding.ser.SimpleListSerializerFactory;
/*  29:    */ import org.apache.axis.encoding.ser.SimpleSerializerFactory;
/*  30:    */ 
/*  31:    */ public class ComprobanteServiceSoapBindingStub
/*  32:    */   extends Stub
/*  33:    */   implements ServicioWebComprobante
/*  34:    */ {
/*  35: 14 */   private Vector cachedSerClasses = new Vector();
/*  36: 15 */   private Vector cachedSerQNames = new Vector();
/*  37: 16 */   private Vector cachedSerFactories = new Vector();
/*  38: 17 */   private Vector cachedDeserFactories = new Vector();
/*  39: 22 */   static OperationDesc[] _operations = new OperationDesc[1];
/*  40:    */   
/*  41:    */   static
/*  42:    */   {
/*  43: 23 */     _initOperationDesc1();
/*  44:    */   }
/*  45:    */   
/*  46:    */   private static void _initOperationDesc1()
/*  47:    */   {
/*  48: 29 */     OperationDesc oper = new OperationDesc();
/*  49: 30 */     oper.setName("insertarClaveAcceso");
/*  50: 31 */     ParameterDesc param = new ParameterDesc(new QName("", "claveAcceso"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  51:    */     
/*  52:    */ 
/*  53: 34 */     param.setOmittable(true);
/*  54: 35 */     oper.addParameter(param);
/*  55: 36 */     param = new ParameterDesc(new QName("", "xml"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  56:    */     
/*  57: 38 */     param.setOmittable(true);
/*  58: 39 */     oper.addParameter(param);
/*  59: 40 */     param = new ParameterDesc(new QName("", "emails"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
/*  60:    */     
/*  61:    */ 
/*  62: 43 */     param.setOmittable(true);
/*  63: 44 */     oper.addParameter(param);
/*  64: 45 */     oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "boolean"));
/*  65: 46 */     oper.setReturnClass(Boolean.TYPE);
/*  66: 47 */     oper.setReturnQName(new QName("", "resultado"));
/*  67: 48 */     oper.setStyle(Style.WRAPPED);
/*  68: 49 */     oper.setUse(Use.LITERAL);
/*  69: 50 */     oper.addFault(new FaultDesc(new QName("http://soap.asinfo.com.ec/", "FacturaeException"), "FacturaeException", new QName("http://soap.asinfo.com.ec/", "FacturaeException"), true));
/*  70:    */     
/*  71: 52 */     _operations[0] = oper;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public ComprobanteServiceSoapBindingStub()
/*  75:    */     throws AxisFault
/*  76:    */   {
/*  77: 57 */     this(null);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public ComprobanteServiceSoapBindingStub(URL endpointURL, javax.xml.rpc.Service service)
/*  81:    */     throws AxisFault
/*  82:    */   {
/*  83: 61 */     this(service);
/*  84: 62 */     this.cachedEndpoint = endpointURL;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public ComprobanteServiceSoapBindingStub(javax.xml.rpc.Service service)
/*  88:    */     throws AxisFault
/*  89:    */   {
/*  90: 66 */     if (service == null) {
/*  91: 67 */       this.service = new org.apache.axis.client.Service();
/*  92:    */     } else {
/*  93: 69 */       this.service = service;
/*  94:    */     }
/*  95: 71 */     ((org.apache.axis.client.Service)this.service).setTypeMappingVersion("1.2");
/*  96:    */     
/*  97:    */ 
/*  98:    */ 
/*  99: 75 */     Class beansf = BeanSerializerFactory.class;
/* 100: 76 */     Class beandf = BeanDeserializerFactory.class;
/* 101: 77 */     Class enumsf = EnumSerializerFactory.class;
/* 102: 78 */     Class enumdf = EnumDeserializerFactory.class;
/* 103: 79 */     Class arraysf = ArraySerializerFactory.class;
/* 104: 80 */     Class arraydf = ArrayDeserializerFactory.class;
/* 105: 81 */     Class simplesf = SimpleSerializerFactory.class;
/* 106: 82 */     Class simpledf = SimpleDeserializerFactory.class;
/* 107: 83 */     Class simplelistsf = SimpleListSerializerFactory.class;
/* 108: 84 */     Class simplelistdf = SimpleListDeserializerFactory.class;
/* 109: 85 */     QName qName = new QName("http://soap.asinfo.com.ec/", "FacturaeException");
/* 110: 86 */     this.cachedSerQNames.add(qName);
/* 111: 87 */     Class cls = FacturaeException.class;
/* 112: 88 */     this.cachedSerClasses.add(cls);
/* 113: 89 */     this.cachedSerFactories.add(beansf);
/* 114: 90 */     this.cachedDeserFactories.add(beandf);
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected Call createCall()
/* 118:    */     throws RemoteException
/* 119:    */   {
/* 120:    */     try
/* 121:    */     {
/* 122: 96 */       Call _call = super._createCall();
/* 123: 97 */       if (this.maintainSessionSet) {
/* 124: 98 */         _call.setMaintainSession(this.maintainSession);
/* 125:    */       }
/* 126:100 */       if (this.cachedUsername != null) {
/* 127:101 */         _call.setUsername(this.cachedUsername);
/* 128:    */       }
/* 129:103 */       if (this.cachedPassword != null) {
/* 130:104 */         _call.setPassword(this.cachedPassword);
/* 131:    */       }
/* 132:106 */       if (this.cachedEndpoint != null) {
/* 133:107 */         _call.setTargetEndpointAddress(this.cachedEndpoint);
/* 134:    */       }
/* 135:109 */       if (this.cachedTimeout != null) {
/* 136:110 */         _call.setTimeout(this.cachedTimeout);
/* 137:    */       }
/* 138:112 */       if (this.cachedPortName != null) {
/* 139:113 */         _call.setPortName(this.cachedPortName);
/* 140:    */       }
/* 141:115 */       Enumeration keys = this.cachedProperties.keys();
/* 142:116 */       while (keys.hasMoreElements())
/* 143:    */       {
/* 144:117 */         String key = (String)keys.nextElement();
/* 145:118 */         _call.setProperty(key, this.cachedProperties.get(key));
/* 146:    */       }
/* 147:125 */       synchronized (this)
/* 148:    */       {
/* 149:126 */         if (firstCall())
/* 150:    */         {
/* 151:128 */           _call.setEncodingStyle(null);
/* 152:129 */           for (int i = 0; i < this.cachedSerFactories.size(); i++)
/* 153:    */           {
/* 154:130 */             Class cls = (Class)this.cachedSerClasses.get(i);
/* 155:131 */             QName qName = (QName)this.cachedSerQNames.get(i);
/* 156:132 */             Object x = this.cachedSerFactories.get(i);
/* 157:133 */             if ((x instanceof Class))
/* 158:    */             {
/* 159:134 */               Class sf = (Class)this.cachedSerFactories.get(i);
/* 160:135 */               Class df = (Class)this.cachedDeserFactories.get(i);
/* 161:136 */               _call.registerTypeMapping(cls, qName, sf, df, false);
/* 162:    */             }
/* 163:137 */             else if ((x instanceof javax.xml.rpc.encoding.SerializerFactory))
/* 164:    */             {
/* 165:138 */               org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)this.cachedSerFactories.get(i);
/* 166:    */               
/* 167:140 */               DeserializerFactory df = (DeserializerFactory)this.cachedDeserFactories.get(i);
/* 168:141 */               _call.registerTypeMapping(cls, qName, sf, df, false);
/* 169:    */             }
/* 170:    */           }
/* 171:    */         }
/* 172:    */       }
/* 173:146 */       return _call;
/* 174:    */     }
/* 175:    */     catch (Throwable _t)
/* 176:    */     {
/* 177:148 */       throw new AxisFault("Failure trying to get the Call object", _t);
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   /* Error */
/* 182:    */   public boolean insertarClaveAcceso(String claveAcceso, String xml, String emails)
/* 183:    */     throws RemoteException, FacturaeException
/* 184:    */   {
/* 185:    */     // Byte code:
/* 186:    */     //   0: aload_0
/* 187:    */     //   1: getfield 35	org/apache/axis/client/Stub:cachedEndpoint	Ljava/net/URL;
/* 188:    */     //   4: ifnonnull +11 -> 15
/* 189:    */     //   7: new 93	org/apache/axis/NoEndPointException
/* 190:    */     //   10: dup
/* 191:    */     //   11: invokespecial 94	org/apache/axis/NoEndPointException:<init>	()V
/* 192:    */     //   14: athrow
/* 193:    */     //   15: aload_0
/* 194:    */     //   16: invokevirtual 95	ec/com/facturae/ws/comprobante/impl/ComprobanteServiceSoapBindingStub:createCall	()Lorg/apache/axis/client/Call;
/* 195:    */     //   19: astore 4
/* 196:    */     //   21: aload 4
/* 197:    */     //   23: getstatic 33	ec/com/facturae/ws/comprobante/impl/ComprobanteServiceSoapBindingStub:_operations	[Lorg/apache/axis/description/OperationDesc;
/* 198:    */     //   26: iconst_0
/* 199:    */     //   27: aaload
/* 200:    */     //   28: invokevirtual 96	org/apache/axis/client/Call:setOperation	(Lorg/apache/axis/description/OperationDesc;)V
/* 201:    */     //   31: aload 4
/* 202:    */     //   33: iconst_1
/* 203:    */     //   34: invokevirtual 97	org/apache/axis/client/Call:setUseSOAPAction	(Z)V
/* 204:    */     //   37: aload 4
/* 205:    */     //   39: ldc 7
/* 206:    */     //   41: invokevirtual 98	org/apache/axis/client/Call:setSOAPActionURI	(Ljava/lang/String;)V
/* 207:    */     //   44: aload 4
/* 208:    */     //   46: aconst_null
/* 209:    */     //   47: invokevirtual 80	org/apache/axis/client/Call:setEncodingStyle	(Ljava/lang/String;)V
/* 210:    */     //   50: aload 4
/* 211:    */     //   52: ldc 100
/* 212:    */     //   54: getstatic 101	java/lang/Boolean:FALSE	Ljava/lang/Boolean;
/* 213:    */     //   57: invokevirtual 78	org/apache/axis/client/Call:setProperty	(Ljava/lang/String;Ljava/lang/Object;)V
/* 214:    */     //   60: aload 4
/* 215:    */     //   62: ldc 103
/* 216:    */     //   64: getstatic 101	java/lang/Boolean:FALSE	Ljava/lang/Boolean;
/* 217:    */     //   67: invokevirtual 78	org/apache/axis/client/Call:setProperty	(Ljava/lang/String;Ljava/lang/Object;)V
/* 218:    */     //   70: aload 4
/* 219:    */     //   72: getstatic 104	org/apache/axis/soap/SOAPConstants:SOAP11_CONSTANTS	Lorg/apache/axis/soap/SOAP11Constants;
/* 220:    */     //   75: invokevirtual 105	org/apache/axis/client/Call:setSOAPVersion	(Lorg/apache/axis/soap/SOAPConstants;)V
/* 221:    */     //   78: aload 4
/* 222:    */     //   80: new 6	javax/xml/namespace/QName
/* 223:    */     //   83: dup
/* 224:    */     //   84: ldc 29
/* 225:    */     //   86: ldc 3
/* 226:    */     //   88: invokespecial 9	javax/xml/namespace/QName:<init>	(Ljava/lang/String;Ljava/lang/String;)V
/* 227:    */     //   91: invokevirtual 106	org/apache/axis/client/Call:setOperationName	(Ljavax/xml/namespace/QName;)V
/* 228:    */     //   94: aload_0
/* 229:    */     //   95: aload 4
/* 230:    */     //   97: invokevirtual 107	ec/com/facturae/ws/comprobante/impl/ComprobanteServiceSoapBindingStub:setRequestHeaders	(Lorg/apache/axis/client/Call;)V
/* 231:    */     //   100: aload_0
/* 232:    */     //   101: aload 4
/* 233:    */     //   103: invokevirtual 108	ec/com/facturae/ws/comprobante/impl/ComprobanteServiceSoapBindingStub:setAttachments	(Lorg/apache/axis/client/Call;)V
/* 234:    */     //   106: aload 4
/* 235:    */     //   108: iconst_3
/* 236:    */     //   109: anewarray 109	java/lang/Object
/* 237:    */     //   112: dup
/* 238:    */     //   113: iconst_0
/* 239:    */     //   114: aload_1
/* 240:    */     //   115: aastore
/* 241:    */     //   116: dup
/* 242:    */     //   117: iconst_1
/* 243:    */     //   118: aload_2
/* 244:    */     //   119: aastore
/* 245:    */     //   120: dup
/* 246:    */     //   121: iconst_2
/* 247:    */     //   122: aload_3
/* 248:    */     //   123: aastore
/* 249:    */     //   124: invokevirtual 110	org/apache/axis/client/Call:invoke	([Ljava/lang/Object;)Ljava/lang/Object;
/* 250:    */     //   127: astore 5
/* 251:    */     //   129: aload 5
/* 252:    */     //   131: instanceof 111
/* 253:    */     //   134: ifeq +9 -> 143
/* 254:    */     //   137: aload 5
/* 255:    */     //   139: checkcast 111	java/rmi/RemoteException
/* 256:    */     //   142: athrow
/* 257:    */     //   143: aload_0
/* 258:    */     //   144: aload 4
/* 259:    */     //   146: invokevirtual 112	ec/com/facturae/ws/comprobante/impl/ComprobanteServiceSoapBindingStub:extractAttachments	(Lorg/apache/axis/client/Call;)V
/* 260:    */     //   149: aload 5
/* 261:    */     //   151: checkcast 113	java/lang/Boolean
/* 262:    */     //   154: invokevirtual 114	java/lang/Boolean:booleanValue	()Z
/* 263:    */     //   157: ireturn
/* 264:    */     //   158: astore 6
/* 265:    */     //   160: aload 5
/* 266:    */     //   162: getstatic 20	java/lang/Boolean:TYPE	Ljava/lang/Class;
/* 267:    */     //   165: invokestatic 116	org/apache/axis/utils/JavaUtils:convert	(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
/* 268:    */     //   168: checkcast 113	java/lang/Boolean
/* 269:    */     //   171: invokevirtual 114	java/lang/Boolean:booleanValue	()Z
/* 270:    */     //   174: ireturn
/* 271:    */     //   175: astore 5
/* 272:    */     //   177: aload 5
/* 273:    */     //   179: getfield 117	org/apache/axis/AxisFault:detail	Ljava/lang/Throwable;
/* 274:    */     //   182: ifnull +43 -> 225
/* 275:    */     //   185: aload 5
/* 276:    */     //   187: getfield 117	org/apache/axis/AxisFault:detail	Ljava/lang/Throwable;
/* 277:    */     //   190: instanceof 111
/* 278:    */     //   193: ifeq +12 -> 205
/* 279:    */     //   196: aload 5
/* 280:    */     //   198: getfield 117	org/apache/axis/AxisFault:detail	Ljava/lang/Throwable;
/* 281:    */     //   201: checkcast 111	java/rmi/RemoteException
/* 282:    */     //   204: athrow
/* 283:    */     //   205: aload 5
/* 284:    */     //   207: getfield 117	org/apache/axis/AxisFault:detail	Ljava/lang/Throwable;
/* 285:    */     //   210: instanceof 59
/* 286:    */     //   213: ifeq +12 -> 225
/* 287:    */     //   216: aload 5
/* 288:    */     //   218: getfield 117	org/apache/axis/AxisFault:detail	Ljava/lang/Throwable;
/* 289:    */     //   221: checkcast 59	ec/com/facturae/ws/comprobante/FacturaeException
/* 290:    */     //   224: athrow
/* 291:    */     //   225: aload 5
/* 292:    */     //   227: athrow
/* 293:    */     // Line number table:
/* 294:    */     //   Java source line #154	-> byte code offset #0
/* 295:    */     //   Java source line #155	-> byte code offset #7
/* 296:    */     //   Java source line #157	-> byte code offset #15
/* 297:    */     //   Java source line #158	-> byte code offset #21
/* 298:    */     //   Java source line #159	-> byte code offset #31
/* 299:    */     //   Java source line #160	-> byte code offset #37
/* 300:    */     //   Java source line #161	-> byte code offset #44
/* 301:    */     //   Java source line #162	-> byte code offset #50
/* 302:    */     //   Java source line #163	-> byte code offset #60
/* 303:    */     //   Java source line #164	-> byte code offset #70
/* 304:    */     //   Java source line #165	-> byte code offset #78
/* 305:    */     //   Java source line #167	-> byte code offset #94
/* 306:    */     //   Java source line #168	-> byte code offset #100
/* 307:    */     //   Java source line #170	-> byte code offset #106
/* 308:    */     //   Java source line #172	-> byte code offset #129
/* 309:    */     //   Java source line #173	-> byte code offset #137
/* 310:    */     //   Java source line #175	-> byte code offset #143
/* 311:    */     //   Java source line #177	-> byte code offset #149
/* 312:    */     //   Java source line #178	-> byte code offset #158
/* 313:    */     //   Java source line #179	-> byte code offset #160
/* 314:    */     //   Java source line #182	-> byte code offset #175
/* 315:    */     //   Java source line #183	-> byte code offset #177
/* 316:    */     //   Java source line #184	-> byte code offset #185
/* 317:    */     //   Java source line #185	-> byte code offset #196
/* 318:    */     //   Java source line #187	-> byte code offset #205
/* 319:    */     //   Java source line #188	-> byte code offset #216
/* 320:    */     //   Java source line #191	-> byte code offset #225
/* 321:    */     // Local variable table:
/* 322:    */     //   start	length	slot	name	signature
/* 323:    */     //   0	228	0	this	ComprobanteServiceSoapBindingStub
/* 324:    */     //   0	228	1	claveAcceso	String
/* 325:    */     //   0	228	2	xml	String
/* 326:    */     //   0	228	3	emails	String
/* 327:    */     //   19	126	4	_call	Call
/* 328:    */     //   127	34	5	_resp	Object
/* 329:    */     //   175	51	5	axisFaultException	AxisFault
/* 330:    */     //   158	3	6	_exception	java.lang.Exception
/* 331:    */     // Exception table:
/* 332:    */     //   from	to	target	type
/* 333:    */     //   149	157	158	java/lang/Exception
/* 334:    */     //   106	157	175	org/apache/axis/AxisFault
/* 335:    */     //   158	174	175	org/apache/axis/AxisFault
/* 336:    */   }
/* 337:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.com.facturae.ws.comprobante.impl.ComprobanteServiceSoapBindingStub
 * JD-Core Version:    0.7.0.1
 */