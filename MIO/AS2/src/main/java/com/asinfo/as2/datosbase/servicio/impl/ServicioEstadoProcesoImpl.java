/*  1:   */ package com.asinfo.as2.datosbase.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.EstadoProcesoDao;
/*  4:   */ import com.asinfo.as2.datosbase.servicio.ServicioEstadoProceso;
/*  5:   */ import com.asinfo.as2.entities.EstadoProceso;
/*  6:   */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  7:   */ import com.asinfo.as2.enumeraciones.Estado;
/*  8:   */ import java.util.List;
/*  9:   */ import javax.ejb.EJB;
/* 10:   */ import javax.ejb.Stateless;
/* 11:   */ 
/* 12:   */ @Stateless
/* 13:   */ public class ServicioEstadoProcesoImpl
/* 14:   */   implements ServicioEstadoProceso
/* 15:   */ {
/* 16:   */   @EJB
/* 17:   */   private EstadoProcesoDao estadoProcesoDao;
/* 18:   */   
/* 19:   */   public void guardar(EstadoProceso tipoVendedor)
/* 20:   */   {
/* 21:39 */     this.estadoProcesoDao.guardar(tipoVendedor);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void eliminar(EstadoProceso tipoVendedor)
/* 25:   */   {
/* 26:45 */     this.estadoProcesoDao.eliminar(tipoVendedor);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public EstadoProceso buscarPorId(int idTipoVendedor)
/* 30:   */   {
/* 31:51 */     return (EstadoProceso)this.estadoProcesoDao.buscarPorId(Integer.valueOf(idTipoVendedor));
/* 32:   */   }
/* 33:   */   
/* 34:   */   public List<Estado> buscarPorDocumentoBase(DocumentoBase documentoBase)
/* 35:   */   {
/* 36:56 */     return this.estadoProcesoDao.buscarPorDocumentoBase(documentoBase);
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.impl.ServicioEstadoProcesoImpl
 * JD-Core Version:    0.7.0.1
 */