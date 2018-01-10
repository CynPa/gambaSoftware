/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Ciudad;
/*  4:   */ import com.asinfo.as2.entities.Ruta;
/*  5:   */ import com.asinfo.as2.entities.TipoVehiculo;
/*  6:   */ import java.util.List;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ 
/*  9:   */ @Stateless
/* 10:   */ public class TipoVehiculoDao
/* 11:   */   extends AbstractDaoAS2<TipoVehiculo>
/* 12:   */ {
/* 13:   */   public TipoVehiculoDao()
/* 14:   */   {
/* 15:12 */     super(TipoVehiculo.class);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public TipoVehiculo cargarDetalle(int idTipoVehiculo)
/* 19:   */   {
/* 20:17 */     TipoVehiculo tipoVehiculo = (TipoVehiculo)buscarPorId(Integer.valueOf(idTipoVehiculo));
/* 21:   */     
/* 22:19 */     tipoVehiculo.getListaRuta().size();
/* 23:21 */     for (Ruta ruta : tipoVehiculo.getListaRuta())
/* 24:   */     {
/* 25:22 */       ruta.getCiudadOrigen().getId();
/* 26:23 */       ruta.getCiudadDestino().getId();
/* 27:   */     }
/* 28:26 */     return tipoVehiculo;
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.TipoVehiculoDao
 * JD-Core Version:    0.7.0.1
 */