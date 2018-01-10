/*   1:    */ package com.asinfo.as2.produccion.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.mantenimiento.old.ProductoRutaFabricacionDao;
/*   4:    */ import com.asinfo.as2.dao.produccion.OperacionProduccionDao;
/*   5:    */ import com.asinfo.as2.dao.produccion.RutaFabricacionDao;
/*   6:    */ import com.asinfo.as2.entities.Producto;
/*   7:    */ import com.asinfo.as2.entities.produccion.OperacionProduccion;
/*   8:    */ import com.asinfo.as2.entities.produccion.ProductoRutaFabricacion;
/*   9:    */ import com.asinfo.as2.entities.produccion.RutaFabricacion;
/*  10:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioRutaFabricacion;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.ejb.Stateless;
/*  15:    */ 
/*  16:    */ @Stateless
/*  17:    */ public class ServicioRutaFabricacionImpl
/*  18:    */   implements ServicioRutaFabricacion
/*  19:    */ {
/*  20:    */   @EJB
/*  21:    */   private RutaFabricacionDao rutaFabricacionDao;
/*  22:    */   @EJB
/*  23:    */   private OperacionProduccionDao operacionProduccionDao;
/*  24:    */   @EJB
/*  25:    */   private ProductoRutaFabricacionDao productoRutaFabricacionDao;
/*  26:    */   
/*  27:    */   public void guardar(RutaFabricacion rutaFabricacion)
/*  28:    */   {
/*  29: 51 */     for (OperacionProduccion operacionProduccion : rutaFabricacion.getListaOperacionProduccion()) {
/*  30: 52 */       this.operacionProduccionDao.guardar(operacionProduccion);
/*  31:    */     }
/*  32: 56 */     for (ProductoRutaFabricacion productoRutaFabricacion : rutaFabricacion.getListaProductoRutaFabricacion()) {
/*  33: 57 */       this.productoRutaFabricacionDao.guardar(productoRutaFabricacion);
/*  34:    */     }
/*  35: 60 */     this.rutaFabricacionDao.guardar(rutaFabricacion);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void eliminar(RutaFabricacion rutaFabricacion)
/*  39:    */   {
/*  40: 70 */     this.rutaFabricacionDao.eliminar(rutaFabricacion);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public RutaFabricacion buscarPorId(int idRutaFabricacion)
/*  44:    */   {
/*  45: 81 */     return (RutaFabricacion)this.rutaFabricacionDao.buscarPorId(Integer.valueOf(idRutaFabricacion));
/*  46:    */   }
/*  47:    */   
/*  48:    */   public List<RutaFabricacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  49:    */   {
/*  50: 92 */     return this.rutaFabricacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public List<RutaFabricacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  54:    */   {
/*  55:102 */     return this.rutaFabricacionDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int contarPorCriterio(Map<String, String> filters)
/*  59:    */   {
/*  60:112 */     return this.rutaFabricacionDao.contarPorCriterio(filters);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public RutaFabricacion cargarDetalle(int idRutaFabricacion)
/*  64:    */   {
/*  65:122 */     return this.rutaFabricacionDao.cargarDetalle(idRutaFabricacion);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public RutaFabricacion copiarRutaFabricacion(RutaFabricacion rutaFabricacionOrigen)
/*  69:    */   {
/*  70:127 */     RutaFabricacion rutaFabricacion = new RutaFabricacion();
/*  71:128 */     rutaFabricacion.setCodigo(rutaFabricacionOrigen.getCodigo());
/*  72:129 */     rutaFabricacion.setDescripcion(rutaFabricacionOrigen.getDescripcion());
/*  73:130 */     rutaFabricacion.setIdOrganizacion(rutaFabricacionOrigen.getIdOrganizacion());
/*  74:131 */     rutaFabricacion.setIdSucursal(rutaFabricacionOrigen.getIdSucursal());
/*  75:132 */     rutaFabricacion.setNombre(rutaFabricacionOrigen.getNombre());
/*  76:133 */     rutaFabricacion.setTipoCicloProduccionEnum(rutaFabricacionOrigen.getTipoCicloProduccionEnum());
/*  77:135 */     for (OperacionProduccion op : rutaFabricacionOrigen.getListaOperacionProduccion())
/*  78:    */     {
/*  79:136 */       OperacionProduccion operacionProduccion = new OperacionProduccion();
/*  80:137 */       operacionProduccion.setCentroTrabajo(op.getCentroTrabajo());
/*  81:138 */       operacionProduccion.setFechaDesde(op.getFechaDesde());
/*  82:139 */       operacionProduccion.setFechaHasta(op.getFechaHasta());
/*  83:140 */       operacionProduccion.setIdOrganizacion(op.getIdOrganizacion());
/*  84:141 */       operacionProduccion.setIdSucursal(op.getIdSucursal());
/*  85:142 */       operacionProduccion.setIndicadorAutomatico(op.isIndicadorAutomatico());
/*  86:143 */       operacionProduccion.setIndicadorFijo(op.isIndicadorFijo());
/*  87:144 */       operacionProduccion.setIndicadorPuntoRecuento(op.isIndicadorPuntoRecuento());
/*  88:145 */       operacionProduccion.setMaquina(op.getMaquina());
/*  89:146 */       operacionProduccion.setNumeroMaquinas(op.getNumeroMaquinas());
/*  90:147 */       operacionProduccion.setNumeroPersonas(op.getNumeroPersonas());
/*  91:148 */       operacionProduccion.setOrden(op.getOrden());
/*  92:149 */       operacionProduccion.setTareaProduccion(op.getTareaProduccion());
/*  93:150 */       operacionProduccion.setRutaFabricacion(rutaFabricacion);
/*  94:151 */       rutaFabricacion.getListaOperacionProduccion().add(operacionProduccion);
/*  95:    */     }
/*  96:153 */     for (ProductoRutaFabricacion pf : rutaFabricacion.getListaProductoRutaFabricacion())
/*  97:    */     {
/*  98:154 */       ProductoRutaFabricacion productoRutaFabricacion = new ProductoRutaFabricacion();
/*  99:155 */       productoRutaFabricacion.setProducto(pf.getProducto());
/* 100:156 */       productoRutaFabricacion.setRutaFabricacion(rutaFabricacion);
/* 101:157 */       rutaFabricacion.getListaProductoRutaFabricacion().add(productoRutaFabricacion);
/* 102:    */     }
/* 103:160 */     return rutaFabricacion;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public List<RutaFabricacion> autocompletarRutaFabricacion(Producto producto, String consulta)
/* 107:    */   {
/* 108:171 */     return this.rutaFabricacionDao.autocompletarRutaFabricacion(producto, consulta);
/* 109:    */   }
/* 110:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.servicio.impl.ServicioRutaFabricacionImpl
 * JD-Core Version:    0.7.0.1
 */