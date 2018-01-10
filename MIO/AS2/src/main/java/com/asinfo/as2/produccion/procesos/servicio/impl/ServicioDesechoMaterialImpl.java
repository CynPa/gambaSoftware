/*   1:    */ package com.asinfo.as2.produccion.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.GenericoDao;
/*   4:    */ import com.asinfo.as2.dao.produccion.OrdenFabricacionDao;
/*   5:    */ import com.asinfo.as2.dao.produccion.OrdenSalidaMaterialDao;
/*   6:    */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterial;
/*   7:    */ import com.asinfo.as2.entities.DetalleOrdenSalidaMaterialOrdenFabricacion;
/*   8:    */ import com.asinfo.as2.entities.LecturaBalanza;
/*   9:    */ import com.asinfo.as2.entities.OrdenFabricacionOrdenSalidaMaterial;
/*  10:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*  11:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  12:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  13:    */ import com.asinfo.as2.produccion.configuracion.servicio.ServicioMarcacionDispositivo;
/*  14:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioDesechoMaterial;
/*  15:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  16:    */ import java.math.BigDecimal;
/*  17:    */ import java.util.List;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.ejb.SessionContext;
/*  20:    */ import javax.ejb.Stateless;
/*  21:    */ import javax.ejb.TransactionAttribute;
/*  22:    */ import javax.ejb.TransactionAttributeType;
/*  23:    */ import javax.ejb.TransactionManagement;
/*  24:    */ import javax.ejb.TransactionManagementType;
/*  25:    */ 
/*  26:    */ @Stateless
/*  27:    */ @TransactionManagement(TransactionManagementType.CONTAINER)
/*  28:    */ public class ServicioDesechoMaterialImpl
/*  29:    */   extends AbstractServicioAS2
/*  30:    */   implements ServicioDesechoMaterial
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 1L;
/*  33:    */   @EJB
/*  34:    */   private OrdenSalidaMaterialDao ordenSalidaMaterialDao;
/*  35:    */   @EJB
/*  36:    */   private GenericoDao<DetalleOrdenSalidaMaterial> detalleOrdenSalidaMaterialDao;
/*  37:    */   @EJB
/*  38:    */   private GenericoDao<LecturaBalanza> lecturaBalanzaDao;
/*  39:    */   @EJB
/*  40:    */   private ServicioMarcacionDispositivo servicioMarcacionDispositivo;
/*  41:    */   @EJB
/*  42:    */   private GenericoDao<DetalleOrdenSalidaMaterialOrdenFabricacion> detalleOrdenSalidaMaterialOrdenFabricacionDao;
/*  43:    */   @EJB
/*  44:    */   private ServicioProducto servicioProducto;
/*  45:    */   @EJB
/*  46:    */   private GenericoDao<OrdenFabricacionOrdenSalidaMaterial> ordenFabricacionOrdenSalidaMaterialDao;
/*  47:    */   @EJB
/*  48:    */   private OrdenFabricacionDao ordenFabricacionDao;
/*  49:    */   
/*  50:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  51:    */   public void agregarPesoDesecho(LecturaBalanza lecturaBalanza)
/*  52:    */     throws AS2Exception
/*  53:    */   {
/*  54:    */     try
/*  55:    */     {
/*  56: 71 */       DetalleOrdenSalidaMaterialOrdenFabricacion detalle = lecturaBalanza.getDetalleOrdenSalidaMaterialOrdenFabricacion();
/*  57: 72 */       BigDecimal[] cantidades = this.servicioMarcacionDispositivo.getCantidades(lecturaBalanza);
/*  58: 73 */       BigDecimal cantidadDesecho = cantidades[0];
/*  59: 74 */       BigDecimal cantidadDesechoInformativa = cantidades[1];
/*  60:    */       
/*  61: 76 */       actualizarCantidadDesecho(detalle, cantidadDesecho, cantidadDesechoInformativa, false);
/*  62:    */       
/*  63: 78 */       this.lecturaBalanzaDao.guardar(lecturaBalanza);
/*  64:    */     }
/*  65:    */     catch (AS2Exception e)
/*  66:    */     {
/*  67: 80 */       this.context.setRollbackOnly();
/*  68: 81 */       throw e;
/*  69:    */     }
/*  70:    */     catch (Exception e)
/*  71:    */     {
/*  72: 83 */       e.printStackTrace();
/*  73: 84 */       this.context.setRollbackOnly();
/*  74: 85 */       throw new AS2Exception(e.getMessage());
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void actualizarCantidadDesecho(DetalleOrdenSalidaMaterialOrdenFabricacion detalle, BigDecimal cantidadDesecho, BigDecimal cantidadDesechoInformativa, boolean indicadorSobreescribir)
/*  79:    */     throws AS2Exception
/*  80:    */   {
/*  81: 92 */     this.ordenFabricacionDao.actualizarCantidadDesecho(detalle, cantidadDesecho, cantidadDesechoInformativa, indicadorSobreescribir);
/*  82:    */   }
/*  83:    */   
/*  84:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  85:    */   public void eliminarPesoDesecho(LecturaBalanza lecturaBalanza)
/*  86:    */     throws AS2Exception
/*  87:    */   {
/*  88:    */     try
/*  89:    */     {
/*  90: 99 */       DetalleOrdenSalidaMaterialOrdenFabricacion detalle = lecturaBalanza.getDetalleOrdenSalidaMaterialOrdenFabricacion();
/*  91:100 */       BigDecimal[] cantidades = this.servicioMarcacionDispositivo.getCantidades(lecturaBalanza);
/*  92:101 */       BigDecimal cantidadDesecho = cantidades[0];
/*  93:102 */       BigDecimal cantidadDesechoInformativa = cantidades[1];
/*  94:    */       
/*  95:104 */       this.ordenFabricacionDao.actualizarCantidadDesecho(detalle, cantidadDesecho.multiply(new BigDecimal(-1)), cantidadDesechoInformativa != null ? cantidadDesechoInformativa
/*  96:105 */         .multiply(new BigDecimal(-1)) : null, false);
/*  97:    */       
/*  98:107 */       this.lecturaBalanzaDao.eliminar(lecturaBalanza);
/*  99:    */     }
/* 100:    */     catch (AS2Exception e)
/* 101:    */     {
/* 102:109 */       this.context.setRollbackOnly();
/* 103:110 */       throw e;
/* 104:    */     }
/* 105:    */     catch (Exception e)
/* 106:    */     {
/* 107:112 */       e.printStackTrace();
/* 108:113 */       this.context.setRollbackOnly();
/* 109:114 */       throw new AS2Exception(e.getMessage());
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/* 114:    */   public void guardarRegistroDesecho(List<DetalleOrdenSalidaMaterialOrdenFabricacion> listaDetalleOrdenSalidaMaterialOrdenFabricacion)
/* 115:    */     throws AS2Exception
/* 116:    */   {}
/* 117:    */   
/* 118:    */   public List<Object[]> getReporteRegistroDesecho(OrdenFabricacion ordenFabricacion)
/* 119:    */   {
/* 120:149 */     return this.ordenFabricacionDao.getReporteRegistroDesecho(ordenFabricacion);
/* 121:    */   }
/* 122:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.servicio.impl.ServicioDesechoMaterialImpl
 * JD-Core Version:    0.7.0.1
 */