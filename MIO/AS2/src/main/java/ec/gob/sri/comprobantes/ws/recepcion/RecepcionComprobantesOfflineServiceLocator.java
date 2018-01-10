/*   1:    */ package ec.gob.sri.comprobantes.ws.recepcion;
/*   2:    */ 
/*   3:    */ import java.net.MalformedURLException;
/*   4:    */ import java.net.URL;
/*   5:    */ import java.rmi.Remote;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import javax.xml.namespace.QName;
/*   9:    */ import javax.xml.rpc.ServiceException;
/*  10:    */ import org.apache.axis.AxisFault;
/*  11:    */ import org.apache.axis.EngineConfiguration;
/*  12:    */ import org.apache.axis.client.Service;
/*  13:    */ import org.apache.axis.client.Stub;
/*  14:    */ 
/*  15:    */ public class RecepcionComprobantesOfflineServiceLocator
/*  16:    */   extends Service
/*  17:    */   implements RecepcionComprobantesOfflineService
/*  18:    */ {
/*  19:    */   public RecepcionComprobantesOfflineServiceLocator() {}
/*  20:    */   
/*  21:    */   public RecepcionComprobantesOfflineServiceLocator(EngineConfiguration config)
/*  22:    */   {
/*  23: 17 */     super(config);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public RecepcionComprobantesOfflineServiceLocator(String wsdlLoc, QName sName)
/*  27:    */     throws ServiceException
/*  28:    */   {
/*  29: 21 */     super(wsdlLoc, sName);
/*  30:    */   }
/*  31:    */   
/*  32: 25 */   private String RecepcionComprobantesOfflinePort_address = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline";
/*  33:    */   
/*  34:    */   public String getRecepcionComprobantesOfflinePortAddress()
/*  35:    */   {
/*  36: 28 */     return this.RecepcionComprobantesOfflinePort_address;
/*  37:    */   }
/*  38:    */   
/*  39: 32 */   private String RecepcionComprobantesOfflinePortWSDDServiceName = "RecepcionComprobantesOfflinePort";
/*  40:    */   
/*  41:    */   public String getRecepcionComprobantesOfflinePortWSDDServiceName()
/*  42:    */   {
/*  43: 35 */     return this.RecepcionComprobantesOfflinePortWSDDServiceName;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setRecepcionComprobantesOfflinePortWSDDServiceName(String name)
/*  47:    */   {
/*  48: 39 */     this.RecepcionComprobantesOfflinePortWSDDServiceName = name;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public RecepcionComprobantesOffline getRecepcionComprobantesOfflinePort()
/*  52:    */     throws ServiceException
/*  53:    */   {
/*  54:    */     try
/*  55:    */     {
/*  56: 45 */       endpoint = new URL(this.RecepcionComprobantesOfflinePort_address);
/*  57:    */     }
/*  58:    */     catch (MalformedURLException e)
/*  59:    */     {
/*  60:    */       URL endpoint;
/*  61: 48 */       throw new ServiceException(e);
/*  62:    */     }
/*  63:    */     URL endpoint;
/*  64: 50 */     return getRecepcionComprobantesOfflinePort(endpoint);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public RecepcionComprobantesOffline getRecepcionComprobantesOfflinePort(URL portAddress)
/*  68:    */     throws ServiceException
/*  69:    */   {
/*  70:    */     try
/*  71:    */     {
/*  72: 55 */       RecepcionComprobantesOfflineServiceSoapBindingStub _stub = new RecepcionComprobantesOfflineServiceSoapBindingStub(portAddress, this);
/*  73: 56 */       _stub.setPortName(getRecepcionComprobantesOfflinePortWSDDServiceName());
/*  74: 57 */       return _stub;
/*  75:    */     }
/*  76:    */     catch (AxisFault e) {}
/*  77: 60 */     return null;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setRecepcionComprobantesOfflinePortEndpointAddress(String address)
/*  81:    */   {
/*  82: 65 */     this.RecepcionComprobantesOfflinePort_address = address;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Remote getPort(Class serviceEndpointInterface)
/*  86:    */     throws ServiceException
/*  87:    */   {
/*  88:    */     try
/*  89:    */     {
/*  90: 75 */       if (RecepcionComprobantesOffline.class.isAssignableFrom(serviceEndpointInterface))
/*  91:    */       {
/*  92: 76 */         RecepcionComprobantesOfflineServiceSoapBindingStub _stub = new RecepcionComprobantesOfflineServiceSoapBindingStub(new URL(this.RecepcionComprobantesOfflinePort_address), this);
/*  93: 77 */         _stub.setPortName(getRecepcionComprobantesOfflinePortWSDDServiceName());
/*  94: 78 */         return _stub;
/*  95:    */       }
/*  96:    */     }
/*  97:    */     catch (Throwable t)
/*  98:    */     {
/*  99: 82 */       throw new ServiceException(t);
/* 100:    */     }
/* 101: 84 */     throw new ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Remote getPort(QName portName, Class serviceEndpointInterface)
/* 105:    */     throws ServiceException
/* 106:    */   {
/* 107: 93 */     if (portName == null) {
/* 108: 94 */       return getPort(serviceEndpointInterface);
/* 109:    */     }
/* 110: 96 */     String inputPortName = portName.getLocalPart();
/* 111: 97 */     if ("RecepcionComprobantesOfflinePort".equals(inputPortName)) {
/* 112: 98 */       return getRecepcionComprobantesOfflinePort();
/* 113:    */     }
/* 114:101 */     Remote _stub = getPort(serviceEndpointInterface);
/* 115:102 */     ((Stub)_stub).setPortName(portName);
/* 116:103 */     return _stub;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public QName getServiceName()
/* 120:    */   {
/* 121:108 */     return new QName("http://ec.gob.sri.ws.recepcion", "RecepcionComprobantesOfflineService");
/* 122:    */   }
/* 123:    */   
/* 124:111 */   private HashSet ports = null;
/* 125:    */   
/* 126:    */   public Iterator getPorts()
/* 127:    */   {
/* 128:114 */     if (this.ports == null)
/* 129:    */     {
/* 130:115 */       this.ports = new HashSet();
/* 131:116 */       this.ports.add(new QName("http://ec.gob.sri.ws.recepcion", "RecepcionComprobantesOfflinePort"));
/* 132:    */     }
/* 133:118 */     return this.ports.iterator();
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setEndpointAddress(String portName, String address)
/* 137:    */     throws ServiceException
/* 138:    */   {
/* 139:126 */     if ("RecepcionComprobantesOfflinePort".equals(portName)) {
/* 140:127 */       setRecepcionComprobantesOfflinePortEndpointAddress(address);
/* 141:    */     } else {
/* 142:131 */       throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setEndpointAddress(QName portName, String address)
/* 147:    */     throws ServiceException
/* 148:    */   {
/* 149:139 */     setEndpointAddress(portName.getLocalPart(), address);
/* 150:    */   }
/* 151:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.gob.sri.comprobantes.ws.recepcion.RecepcionComprobantesOfflineServiceLocator
 * JD-Core Version:    0.7.0.1
 */