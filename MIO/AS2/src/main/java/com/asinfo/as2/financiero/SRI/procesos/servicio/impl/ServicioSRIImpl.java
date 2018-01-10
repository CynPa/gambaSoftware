/*  1:   */ package com.asinfo.as2.financiero.SRI.procesos.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.sri.CreditoTributarioSRIDao;
/*  4:   */ import com.asinfo.as2.dao.sri.TipoComprobanteSRIDao;
/*  5:   */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  6:   */ import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
/*  7:   */ import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
/*  8:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  9:   */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioSRI;
/* 10:   */ import java.util.List;
/* 11:   */ import javax.ejb.EJB;
/* 12:   */ import javax.ejb.Stateless;
/* 13:   */ 
/* 14:   */ @Stateless
/* 15:   */ public class ServicioSRIImpl
/* 16:   */   implements ServicioSRI
/* 17:   */ {
/* 18:   */   @EJB
/* 19:   */   private CreditoTributarioSRIDao creditoTributarioSRIDao;
/* 20:   */   @EJB
/* 21:   */   private TipoComprobanteSRIDao tipoComprobanteSRIDao;
/* 22:   */   
/* 23:   */   public List<CreditoTributarioSRI> obtenerCreditoTributarioSRI()
/* 24:   */   {
/* 25:49 */     return this.creditoTributarioSRIDao.obtenerListaCombo("codigo", true, null);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public List<TipoComprobanteSRI> obtenerTipoComprobanteSRI()
/* 29:   */   {
/* 30:59 */     return this.tipoComprobanteSRIDao.obtenerListaCombo("nombre", true, null);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public TipoComprobanteSRI buscarTipoComprobanteSRIPorCodigo(String codigo)
/* 34:   */     throws ExcepcionAS2
/* 35:   */   {
/* 36:69 */     return this.tipoComprobanteSRIDao.buscarPorCodigo(codigo);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public TipoComprobanteSRI buscarTipoComprobanteSRIPorId(int idipoComprobanteSRI)
/* 40:   */     throws ExcepcionAS2
/* 41:   */   {
/* 42:79 */     return (TipoComprobanteSRI)this.tipoComprobanteSRIDao.buscarPorId(Integer.valueOf(idipoComprobanteSRI));
/* 43:   */   }
/* 44:   */   
/* 45:   */   public List<TipoComprobanteSRI> buscarPorTipoIdentificacion(TipoIdentificacion tipoIdentificacion)
/* 46:   */   {
/* 47:84 */     return this.tipoComprobanteSRIDao.buscarPorTipoIdentificacion(tipoIdentificacion);
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.SRI.procesos.servicio.impl.ServicioSRIImpl
 * JD-Core Version:    0.7.0.1
 */