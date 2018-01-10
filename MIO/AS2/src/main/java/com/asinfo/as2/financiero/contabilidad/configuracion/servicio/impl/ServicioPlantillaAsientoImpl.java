/*   1:    */ package com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.DetallePlantillaAsientoDao;
/*   4:    */ import com.asinfo.as2.dao.PlantillaAsientoDao;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.DetallePlantillaAsiento;
/*   7:    */ import com.asinfo.as2.entities.PlantillaAsiento;
/*   8:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   9:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioPlantillaAsiento;
/*  10:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  11:    */ import java.math.BigDecimal;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.ejb.Stateless;
/*  16:    */ 
/*  17:    */ @Stateless
/*  18:    */ public class ServicioPlantillaAsientoImpl
/*  19:    */   implements ServicioPlantillaAsiento
/*  20:    */ {
/*  21:    */   @EJB
/*  22:    */   private PlantillaAsientoDao plantillaAsientoDao;
/*  23:    */   @EJB
/*  24:    */   private DetallePlantillaAsientoDao detallePlantillaAsientoDao;
/*  25:    */   
/*  26:    */   public void guardar(PlantillaAsiento plantillaAsiento)
/*  27:    */     throws AS2Exception
/*  28:    */   {
/*  29: 50 */     validar(plantillaAsiento);
/*  30: 51 */     for (DetallePlantillaAsiento detalle : plantillaAsiento.getListaDetallePlantillaAsiento()) {
/*  31: 52 */       this.detallePlantillaAsientoDao.guardar(detalle);
/*  32:    */     }
/*  33: 54 */     this.plantillaAsientoDao.guardar(plantillaAsiento);
/*  34:    */   }
/*  35:    */   
/*  36:    */   private void validar(PlantillaAsiento plantillaAsiento)
/*  37:    */     throws AS2Exception
/*  38:    */   {
/*  39: 64 */     calcularTotales(plantillaAsiento);
/*  40: 65 */     if (plantillaAsiento.isIndicadorPorcentaje())
/*  41:    */     {
/*  42: 66 */       if (plantillaAsiento.getTotalDebe().compareTo(new BigDecimal(200)) >= 0) {
/*  43: 67 */         throw new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_PORCENTAJE_PLANTILLA_CONTABLE", new String[] { plantillaAsiento.getTotalDebe().toString(), plantillaAsiento.getTotalHaber().toString() });
/*  44:    */       }
/*  45: 69 */       if (plantillaAsiento.getTotalDebe().add(plantillaAsiento.getTotalHaber()).doubleValue() % 100.0D != 0.0D) {
/*  46: 70 */         throw new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_PORCENTAJE_PLANTILLA_CONTABLE", new String[] { plantillaAsiento.getTotalDebe().toString(), plantillaAsiento.getTotalHaber().toString() });
/*  47:    */       }
/*  48:    */     }
/*  49: 75 */     for (DetallePlantillaAsiento detalle : plantillaAsiento.getListaDetallePlantillaAsiento()) {
/*  50: 77 */       if (!detalle.isEliminado())
/*  51:    */       {
/*  52: 78 */         if ((detalle.getCuentaContable().isIndicadorValidarDimension1()) && (detalle.getDimensionContable1() == null)) {
/*  53: 79 */           throw new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_DIMENSION_CONTABLE", new String[] { detalle.getCuentaContable().getCodigo(), detalle.getCuentaContable().getNombre(), "1" });
/*  54:    */         }
/*  55: 81 */         if ((detalle.getCuentaContable().isIndicadorValidarDimension2()) && (detalle.getDimensionContable2() == null)) {
/*  56: 82 */           throw new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_DIMENSION_CONTABLE", new String[] { detalle.getCuentaContable().getCodigo(), detalle.getCuentaContable().getNombre(), "2" });
/*  57:    */         }
/*  58: 84 */         if ((detalle.getCuentaContable().isIndicadorValidarDimension3()) && (detalle.getDimensionContable3() == null)) {
/*  59: 85 */           throw new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_DIMENSION_CONTABLE", new String[] { detalle.getCuentaContable().getCodigo(), detalle.getCuentaContable().getNombre(), "3" });
/*  60:    */         }
/*  61: 87 */         if ((detalle.getCuentaContable().isIndicadorValidarDimension4()) && (detalle.getDimensionContable4() == null)) {
/*  62: 88 */           throw new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_DIMENSION_CONTABLE", new String[] { detalle.getCuentaContable().getCodigo(), detalle.getCuentaContable().getNombre(), "4" });
/*  63:    */         }
/*  64: 90 */         if ((detalle.getCuentaContable().isIndicadorValidarDimension5()) && (detalle.getDimensionContable5() == null)) {
/*  65: 91 */           throw new AS2Exception("com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl.ERROR_DIMENSION_CONTABLE", new String[] { detalle.getCuentaContable().getCodigo(), detalle.getCuentaContable().getNombre(), "5" });
/*  66:    */         }
/*  67:    */       }
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void anular(PlantillaAsiento plantillaAsiento)
/*  72:    */     throws ExcepcionAS2Financiero
/*  73:    */   {}
/*  74:    */   
/*  75:    */   public PlantillaAsiento buscarPorId(Integer id)
/*  76:    */   {
/*  77:116 */     return (PlantillaAsiento)this.plantillaAsientoDao.buscarPorId(id);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public List<PlantillaAsiento> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  81:    */   {
/*  82:126 */     return this.plantillaAsientoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public List<PlantillaAsiento> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  86:    */   {
/*  87:136 */     return this.plantillaAsientoDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public PlantillaAsiento cargarDetalle(Integer idPlantillaAsiento)
/*  91:    */   {
/*  92:146 */     return this.plantillaAsientoDao.cargarDetalle(idPlantillaAsiento);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int contarPorCriterio(Map<String, String> filters)
/*  96:    */   {
/*  97:156 */     return this.plantillaAsientoDao.contarPorCriterio(filters);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void calcularTotales(PlantillaAsiento plantillaAsiento)
/* 101:    */   {
/* 102:166 */     plantillaAsiento.setTotalDebe(BigDecimal.ZERO);
/* 103:167 */     plantillaAsiento.setTotalHaber(BigDecimal.ZERO);
/* 104:168 */     for (DetallePlantillaAsiento detalle : plantillaAsiento.getListaDetallePlantillaAsiento()) {
/* 105:169 */       if (!detalle.isEliminado())
/* 106:    */       {
/* 107:170 */         plantillaAsiento.setTotalDebe(plantillaAsiento.getTotalDebe().add(detalle.getDebe()));
/* 108:171 */         plantillaAsiento.setTotalHaber(plantillaAsiento.getTotalHaber().add(detalle.getHaber()));
/* 109:    */       }
/* 110:    */     }
/* 111:    */   }
/* 112:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioPlantillaAsientoImpl
 * JD-Core Version:    0.7.0.1
 */