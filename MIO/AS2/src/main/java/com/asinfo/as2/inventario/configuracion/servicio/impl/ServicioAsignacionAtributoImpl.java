/*  1:   */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AsignacionAtributoDao;
/*  4:   */ import com.asinfo.as2.entities.AsignacionAtributo;
/*  5:   */ import com.asinfo.as2.entities.Atributo;
/*  6:   */ import com.asinfo.as2.entities.ConjuntoAtributo;
/*  7:   */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAsignacionAtributo;
/*  8:   */ import java.util.List;
/*  9:   */ import javax.ejb.EJB;
/* 10:   */ import javax.ejb.Stateless;
/* 11:   */ 
/* 12:   */ @Stateless
/* 13:   */ public class ServicioAsignacionAtributoImpl
/* 14:   */   implements ServicioAsignacionAtributo
/* 15:   */ {
/* 16:   */   @EJB
/* 17:   */   private AsignacionAtributoDao asignacionAtributoDao;
/* 18:   */   
/* 19:   */   public void guardar(AsignacionAtributo asignacionAtributo)
/* 20:   */   {
/* 21:40 */     this.asignacionAtributoDao.guardar(asignacionAtributo);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void eliminar(AsignacionAtributo asignacionAtributo)
/* 25:   */   {
/* 26:48 */     this.asignacionAtributoDao.eliminar(asignacionAtributo);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public AsignacionAtributo buscarPorId(int id)
/* 30:   */   {
/* 31:56 */     return (AsignacionAtributo)this.asignacionAtributoDao.buscarPorId(Integer.valueOf(id));
/* 32:   */   }
/* 33:   */   
/* 34:   */   public List<Atributo> obtenerAtributosPorConjunto(ConjuntoAtributo conjuntoAtributo)
/* 35:   */   {
/* 36:73 */     return null;
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioAsignacionAtributoImpl
 * JD-Core Version:    0.7.0.1
 */