/*   1:    */ package ec.com.facturae.ws.comprobante.impl;
/*   2:    */ 
/*   3:    */ import ec.com.facturae.ws.comprobante.ServicioWebComprobante;
/*   4:    */ import java.net.MalformedURLException;
/*   5:    */ import java.net.URL;
/*   6:    */ import java.rmi.Remote;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import javax.xml.namespace.QName;
/*  10:    */ import javax.xml.rpc.ServiceException;
/*  11:    */ import org.apache.axis.AxisFault;
/*  12:    */ import org.apache.axis.EngineConfiguration;
/*  13:    */ import org.apache.axis.client.Service;
/*  14:    */ import org.apache.axis.client.Stub;
/*  15:    */ 
/*  16:    */ public class ComprobanteServiceLocator
/*  17:    */   extends Service
/*  18:    */   implements ComprobanteService
/*  19:    */ {
/*  20:    */   public ComprobanteServiceLocator() {}
/*  21:    */   
/*  22:    */   public ComprobanteServiceLocator(EngineConfiguration config)
/*  23:    */   {
/*  24: 19 */     super(config);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public ComprobanteServiceLocator(String wsdlLoc, QName sName)
/*  28:    */     throws ServiceException
/*  29:    */   {
/*  30: 23 */     super(wsdlLoc, sName);
/*  31:    */   }
/*  32:    */   
/*  33: 27 */   private String ComprobantePort_address = "http://server.asinfo-cloud.com:8080/facturae-web/ws/factura";
/*  34:    */   
/*  35:    */   public String getComprobantePortAddress()
/*  36:    */   {
/*  37: 30 */     return this.ComprobantePort_address;
/*  38:    */   }
/*  39:    */   
/*  40: 34 */   private String ComprobantePortWSDDServiceName = "ComprobantePort";
/*  41:    */   
/*  42:    */   public String getComprobantePortWSDDServiceName()
/*  43:    */   {
/*  44: 37 */     return this.ComprobantePortWSDDServiceName;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setComprobantePortWSDDServiceName(String name)
/*  48:    */   {
/*  49: 41 */     this.ComprobantePortWSDDServiceName = name;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public ServicioWebComprobante getComprobantePort()
/*  53:    */     throws ServiceException
/*  54:    */   {
/*  55:    */     try
/*  56:    */     {
/*  57: 47 */       endpoint = new URL(this.ComprobantePort_address);
/*  58:    */     }
/*  59:    */     catch (MalformedURLException e)
/*  60:    */     {
/*  61:    */       URL endpoint;
/*  62: 50 */       throw new ServiceException(e);
/*  63:    */     }
/*  64:    */     URL endpoint;
/*  65: 52 */     return getComprobantePort(endpoint);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public ServicioWebComprobante getComprobantePort(URL portAddress)
/*  69:    */     throws ServiceException
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73: 57 */       ComprobanteServiceSoapBindingStub _stub = new ComprobanteServiceSoapBindingStub(portAddress, this);
/*  74: 58 */       _stub.setPortName(getComprobantePortWSDDServiceName());
/*  75: 59 */       return _stub;
/*  76:    */     }
/*  77:    */     catch (AxisFault e) {}
/*  78: 62 */     return null;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setComprobantePortEndpointAddress(String address)
/*  82:    */   {
/*  83: 67 */     this.ComprobantePort_address = address;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Remote getPort(Class serviceEndpointInterface)
/*  87:    */     throws ServiceException
/*  88:    */   {
/*  89:    */     try
/*  90:    */     {
/*  91: 77 */       if (ServicioWebComprobante.class.isAssignableFrom(serviceEndpointInterface))
/*  92:    */       {
/*  93: 78 */         ComprobanteServiceSoapBindingStub _stub = new ComprobanteServiceSoapBindingStub(new URL(this.ComprobantePort_address), this);
/*  94: 79 */         _stub.setPortName(getComprobantePortWSDDServiceName());
/*  95: 80 */         return _stub;
/*  96:    */       }
/*  97:    */     }
/*  98:    */     catch (Throwable t)
/*  99:    */     {
/* 100: 84 */       throw new ServiceException(t);
/* 101:    */     }
/* 102: 86 */     throw new ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Remote getPort(QName portName, Class serviceEndpointInterface)
/* 106:    */     throws ServiceException
/* 107:    */   {
/* 108: 95 */     if (portName == null) {
/* 109: 96 */       return getPort(serviceEndpointInterface);
/* 110:    */     }
/* 111: 98 */     String inputPortName = portName.getLocalPart();
/* 112: 99 */     if ("ComprobantePort".equals(inputPortName)) {
/* 113:100 */       return getComprobantePort();
/* 114:    */     }
/* 115:103 */     Remote _stub = getPort(serviceEndpointInterface);
/* 116:104 */     ((Stub)_stub).setPortName(portName);
/* 117:105 */     return _stub;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public QName getServiceName()
/* 121:    */   {
/* 122:110 */     return new QName("http://impl.soap.asinfo.com.ec/", "ComprobanteService");
/* 123:    */   }
/* 124:    */   
/* 125:113 */   private HashSet ports = null;
/* 126:    */   
/* 127:    */   public Iterator getPorts()
/* 128:    */   {
/* 129:116 */     if (this.ports == null)
/* 130:    */     {
/* 131:117 */       this.ports = new HashSet();
/* 132:118 */       this.ports.add(new QName("http://impl.soap.asinfo.com.ec/", "ComprobantePort"));
/* 133:    */     }
/* 134:120 */     return this.ports.iterator();
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setEndpointAddress(String portName, String address)
/* 138:    */     throws ServiceException
/* 139:    */   {
/* 140:128 */     if ("ComprobantePort".equals(portName)) {
/* 141:129 */       setComprobantePortEndpointAddress(address);
/* 142:    */     } else {
/* 143:133 */       throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
/* 144:    */     }
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setEndpointAddress(QName portName, String address)
/* 148:    */     throws ServiceException
/* 149:    */   {
/* 150:141 */     setEndpointAddress(portName.getLocalPart(), address);
/* 151:    */   }
/* 152:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.com.facturae.ws.comprobante.impl.ComprobanteServiceLocator
 * JD-Core Version:    0.7.0.1
 */