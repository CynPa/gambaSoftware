/*  1:   */ package ec.gob.sri.comprobantes.ws.autorizacion;
/*  2:   */ 
/*  3:   */ import java.rmi.RemoteException;
/*  4:   */ import javax.xml.rpc.ServiceException;
/*  5:   */ import javax.xml.rpc.Stub;
/*  6:   */ 
/*  7:   */ public class AutorizacionComprobantesOfflineProxy
/*  8:   */   implements AutorizacionComprobantesOffline
/*  9:   */ {
/* 10: 4 */   private String _endpoint = null;
/* 11: 5 */   private AutorizacionComprobantesOffline autorizacionComprobantesOffline = null;
/* 12:   */   
/* 13:   */   public AutorizacionComprobantesOfflineProxy()
/* 14:   */   {
/* 15: 8 */     _initAutorizacionComprobantesOfflineProxy();
/* 16:   */   }
/* 17:   */   
/* 18:   */   public AutorizacionComprobantesOfflineProxy(String endpoint)
/* 19:   */   {
/* 20:12 */     this._endpoint = endpoint;
/* 21:13 */     _initAutorizacionComprobantesOfflineProxy();
/* 22:   */   }
/* 23:   */   
/* 24:   */   private void _initAutorizacionComprobantesOfflineProxy()
/* 25:   */   {
/* 26:   */     try
/* 27:   */     {
/* 28:18 */       this.autorizacionComprobantesOffline = new AutorizacionComprobantesOfflineServiceLocator().getAutorizacionComprobantesOfflinePort();
/* 29:19 */       if (this.autorizacionComprobantesOffline != null) {
/* 30:20 */         if (this._endpoint != null) {
/* 31:21 */           ((Stub)this.autorizacionComprobantesOffline)._setProperty("javax.xml.rpc.service.endpoint.address", this._endpoint);
/* 32:   */         } else {
/* 33:23 */           this._endpoint = ((String)((Stub)this.autorizacionComprobantesOffline)._getProperty("javax.xml.rpc.service.endpoint.address"));
/* 34:   */         }
/* 35:   */       }
/* 36:   */     }
/* 37:   */     catch (ServiceException localServiceException) {}
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String getEndpoint()
/* 41:   */   {
/* 42:31 */     return this._endpoint;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void setEndpoint(String endpoint)
/* 46:   */   {
/* 47:35 */     this._endpoint = endpoint;
/* 48:36 */     if (this.autorizacionComprobantesOffline != null) {
/* 49:37 */       ((Stub)this.autorizacionComprobantesOffline)._setProperty("javax.xml.rpc.service.endpoint.address", this._endpoint);
/* 50:   */     }
/* 51:   */   }
/* 52:   */   
/* 53:   */   public AutorizacionComprobantesOffline getAutorizacionComprobantesOffline()
/* 54:   */   {
/* 55:42 */     if (this.autorizacionComprobantesOffline == null) {
/* 56:43 */       _initAutorizacionComprobantesOfflineProxy();
/* 57:   */     }
/* 58:44 */     return this.autorizacionComprobantesOffline;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public RespuestaComprobante autorizacionComprobante(String claveAccesoComprobante)
/* 62:   */     throws RemoteException
/* 63:   */   {
/* 64:48 */     if (this.autorizacionComprobantesOffline == null) {
/* 65:49 */       _initAutorizacionComprobantesOfflineProxy();
/* 66:   */     }
/* 67:50 */     return this.autorizacionComprobantesOffline.autorizacionComprobante(claveAccesoComprobante);
/* 68:   */   }
/* 69:   */   
/* 70:   */   public RespuestaLote autorizacionComprobanteLote(String claveAccesoLote)
/* 71:   */     throws RemoteException
/* 72:   */   {
/* 73:54 */     if (this.autorizacionComprobantesOffline == null) {
/* 74:55 */       _initAutorizacionComprobantesOfflineProxy();
/* 75:   */     }
/* 76:56 */     return this.autorizacionComprobantesOffline.autorizacionComprobanteLote(claveAccesoLote);
/* 77:   */   }
/* 78:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.gob.sri.comprobantes.ws.autorizacion.AutorizacionComprobantesOfflineProxy
 * JD-Core Version:    0.7.0.1
 */