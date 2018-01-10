/*  1:   */ package ec.com.facturae.ws.comprobante;
/*  2:   */ 
/*  3:   */ import ec.com.facturae.ws.comprobante.impl.ComprobanteServiceLocator;
/*  4:   */ import java.rmi.RemoteException;
/*  5:   */ import javax.xml.rpc.ServiceException;
/*  6:   */ import javax.xml.rpc.Stub;
/*  7:   */ 
/*  8:   */ public class ServicioWebComprobanteProxy
/*  9:   */   implements ServicioWebComprobante
/* 10:   */ {
/* 11: 6 */   private String _endpoint = null;
/* 12: 7 */   private ServicioWebComprobante servicioWebComprobante = null;
/* 13:   */   
/* 14:   */   public ServicioWebComprobanteProxy()
/* 15:   */   {
/* 16:10 */     _initServicioWebComprobanteProxy();
/* 17:   */   }
/* 18:   */   
/* 19:   */   public ServicioWebComprobanteProxy(String endpoint)
/* 20:   */   {
/* 21:14 */     this._endpoint = endpoint;
/* 22:15 */     _initServicioWebComprobanteProxy();
/* 23:   */   }
/* 24:   */   
/* 25:   */   private void _initServicioWebComprobanteProxy()
/* 26:   */   {
/* 27:   */     try
/* 28:   */     {
/* 29:20 */       this.servicioWebComprobante = new ComprobanteServiceLocator().getComprobantePort();
/* 30:21 */       if (this.servicioWebComprobante != null) {
/* 31:22 */         if (this._endpoint != null) {
/* 32:23 */           ((Stub)this.servicioWebComprobante)._setProperty("javax.xml.rpc.service.endpoint.address", this._endpoint);
/* 33:   */         } else {
/* 34:25 */           this._endpoint = ((String)((Stub)this.servicioWebComprobante)._getProperty("javax.xml.rpc.service.endpoint.address"));
/* 35:   */         }
/* 36:   */       }
/* 37:   */     }
/* 38:   */     catch (ServiceException localServiceException) {}
/* 39:   */   }
/* 40:   */   
/* 41:   */   public String getEndpoint()
/* 42:   */   {
/* 43:33 */     return this._endpoint;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void setEndpoint(String endpoint)
/* 47:   */   {
/* 48:37 */     this._endpoint = endpoint;
/* 49:38 */     if (this.servicioWebComprobante != null) {
/* 50:39 */       ((Stub)this.servicioWebComprobante)._setProperty("javax.xml.rpc.service.endpoint.address", this._endpoint);
/* 51:   */     }
/* 52:   */   }
/* 53:   */   
/* 54:   */   public ServicioWebComprobante getServicioWebComprobante()
/* 55:   */   {
/* 56:44 */     if (this.servicioWebComprobante == null) {
/* 57:45 */       _initServicioWebComprobanteProxy();
/* 58:   */     }
/* 59:46 */     return this.servicioWebComprobante;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public boolean insertarClaveAcceso(String claveAcceso, String xml, String emails)
/* 63:   */     throws RemoteException, FacturaeException
/* 64:   */   {
/* 65:51 */     if (this.servicioWebComprobante == null) {
/* 66:52 */       _initServicioWebComprobanteProxy();
/* 67:   */     }
/* 68:53 */     return this.servicioWebComprobante.insertarClaveAcceso(claveAcceso, xml, emails);
/* 69:   */   }
/* 70:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.com.facturae.ws.comprobante.ServicioWebComprobanteProxy
 * JD-Core Version:    0.7.0.1
 */