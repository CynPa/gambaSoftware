/*  1:   */ package ec.gob.sri.comprobantes.ws.recepcion;
/*  2:   */ 
/*  3:   */ import java.rmi.RemoteException;
/*  4:   */ import javax.xml.rpc.ServiceException;
/*  5:   */ import javax.xml.rpc.Stub;
/*  6:   */ 
/*  7:   */ public class RecepcionComprobantesOfflineProxy
/*  8:   */   implements RecepcionComprobantesOffline
/*  9:   */ {
/* 10: 4 */   private String _endpoint = null;
/* 11: 5 */   private RecepcionComprobantesOffline recepcionComprobantesOffline = null;
/* 12:   */   
/* 13:   */   public RecepcionComprobantesOfflineProxy()
/* 14:   */   {
/* 15: 8 */     _initRecepcionComprobantesOfflineProxy();
/* 16:   */   }
/* 17:   */   
/* 18:   */   public RecepcionComprobantesOfflineProxy(String endpoint)
/* 19:   */   {
/* 20:12 */     this._endpoint = endpoint;
/* 21:13 */     _initRecepcionComprobantesOfflineProxy();
/* 22:   */   }
/* 23:   */   
/* 24:   */   private void _initRecepcionComprobantesOfflineProxy()
/* 25:   */   {
/* 26:   */     try
/* 27:   */     {
/* 28:18 */       this.recepcionComprobantesOffline = new RecepcionComprobantesOfflineServiceLocator().getRecepcionComprobantesOfflinePort();
/* 29:19 */       if (this.recepcionComprobantesOffline != null) {
/* 30:20 */         if (this._endpoint != null) {
/* 31:21 */           ((Stub)this.recepcionComprobantesOffline)._setProperty("javax.xml.rpc.service.endpoint.address", this._endpoint);
/* 32:   */         } else {
/* 33:23 */           this._endpoint = ((String)((Stub)this.recepcionComprobantesOffline)._getProperty("javax.xml.rpc.service.endpoint.address"));
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
/* 48:36 */     if (this.recepcionComprobantesOffline != null) {
/* 49:37 */       ((Stub)this.recepcionComprobantesOffline)._setProperty("javax.xml.rpc.service.endpoint.address", this._endpoint);
/* 50:   */     }
/* 51:   */   }
/* 52:   */   
/* 53:   */   public RecepcionComprobantesOffline getRecepcionComprobantesOffline()
/* 54:   */   {
/* 55:42 */     if (this.recepcionComprobantesOffline == null) {
/* 56:43 */       _initRecepcionComprobantesOfflineProxy();
/* 57:   */     }
/* 58:44 */     return this.recepcionComprobantesOffline;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public RespuestaSolicitud validarComprobante(byte[] xml)
/* 62:   */     throws RemoteException
/* 63:   */   {
/* 64:48 */     if (this.recepcionComprobantesOffline == null) {
/* 65:49 */       _initRecepcionComprobantesOfflineProxy();
/* 66:   */     }
/* 67:50 */     return this.recepcionComprobantesOffline.validarComprobante(xml);
/* 68:   */   }
/* 69:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.gob.sri.comprobantes.ws.recepcion.RecepcionComprobantesOfflineProxy
 * JD-Core Version:    0.7.0.1
 */