/*   1:    */ package com.asinfo.as2.configuracionbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursalRemoto;
/*   5:    */ import com.asinfo.as2.dao.ContactoDao;
/*   6:    */ import com.asinfo.as2.dao.SucursalDao;
/*   7:    */ import com.asinfo.as2.dao.UbicacionDao;
/*   8:    */ import com.asinfo.as2.dao.seguridad.UsuarioDao;
/*   9:    */ import com.asinfo.as2.entities.Contacto;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.entities.Ubicacion;
/*  12:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  13:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.ejb.Stateless;
/*  19:    */ 
/*  20:    */ @Stateless
/*  21:    */ public class ServicioSucursalImpl
/*  22:    */   implements ServicioSucursal, ServicioSucursalRemoto
/*  23:    */ {
/*  24:    */   @EJB
/*  25:    */   private SucursalDao sucursalDao;
/*  26:    */   @EJB
/*  27:    */   private UbicacionDao ubicacionDao;
/*  28:    */   @EJB
/*  29:    */   private UsuarioDao usuarioDao;
/*  30:    */   @EJB
/*  31:    */   private ContactoDao contactoDao;
/*  32:    */   
/*  33:    */   public Sucursal guardar(Sucursal entidad)
/*  34:    */     throws ExcepcionAS2Financiero
/*  35:    */   {
/*  36: 54 */     if (entidad.getCodigo().equals("000")) {
/*  37: 55 */       throw new ExcepcionAS2Financiero("msg_error_codigo_serie_autorizacion", " Establecimiento");
/*  38:    */     }
/*  39: 57 */     if (entidad.getUbicacion().getDireccion1().isEmpty()) {
/*  40: 58 */       throw new ExcepcionAS2Financiero("msg_error_direccion_sucursal", " Sucursal");
/*  41:    */     }
/*  42: 60 */     if (entidad.getUbicacion() != null) {
/*  43: 61 */       this.ubicacionDao.guardar(entidad.getUbicacion());
/*  44:    */     }
/*  45: 63 */     for (Contacto contacto : entidad.getListaContacto()) {
/*  46: 64 */       this.contactoDao.guardar(contacto);
/*  47:    */     }
/*  48: 66 */     this.sucursalDao.guardar(entidad);
/*  49: 67 */     return entidad;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void eliminar(Sucursal entidad)
/*  53:    */   {
/*  54: 77 */     this.sucursalDao.eliminar(entidad);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public List<Sucursal> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  58:    */   {
/*  59: 89 */     return this.sucursalDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public List<Sucursal> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  63:    */   {
/*  64: 99 */     return this.sucursalDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int contarPorCriterio(Map<String, String> filters)
/*  68:    */   {
/*  69:109 */     return this.sucursalDao.contarPorCriterio(filters);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Sucursal buscarPorId(Integer id)
/*  73:    */   {
/*  74:119 */     return (Sucursal)this.sucursalDao.buscarPorId(id);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public List<Sucursal> obtenerListaComboPorUsuario(int idUsuario, int idOrganizacion)
/*  78:    */   {
/*  79:129 */     EntidadUsuario usuario = (EntidadUsuario)this.usuarioDao.buscarPorId(Integer.valueOf(idUsuario));
/*  80:131 */     if (usuario.isIndicadorSuperAdministrador())
/*  81:    */     {
/*  82:132 */       Map<String, String> filtro = new HashMap();
/*  83:133 */       filtro.put("idOrganizacion", "" + idOrganizacion);
/*  84:134 */       return obtenerListaCombo("predeterminado", false, filtro);
/*  85:    */     }
/*  86:137 */     return this.sucursalDao.obtenerListaComboPorUsuario(idUsuario, idOrganizacion);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Sucursal cargarDetalle(int idSucursal)
/*  90:    */   {
/*  91:147 */     return this.sucursalDao.cargarDetalle(idSucursal);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Long getNumeroEstablecimientosPorOrganizacion(int idOrganizacion)
/*  95:    */   {
/*  96:152 */     return this.sucursalDao.getNumeroEstablecimientosPorOrganizacion(idOrganizacion);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Sucursal buscarPorCodigo(int idOrganizacion, String codigo)
/* 100:    */   {
/* 101:157 */     return this.sucursalDao.buscarPorCodigo(idOrganizacion, codigo);
/* 102:    */   }
/* 103:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.impl.ServicioSucursalImpl
 * JD-Core Version:    0.7.0.1
 */