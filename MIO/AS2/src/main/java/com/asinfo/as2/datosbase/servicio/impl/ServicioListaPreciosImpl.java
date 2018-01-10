/*   1:    */ package com.asinfo.as2.datosbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.DetalleVersionListaPreciosDao;
/*   4:    */ import com.asinfo.as2.dao.ListaPreciosDao;
/*   5:    */ import com.asinfo.as2.dao.VersionListaPreciosDao;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaPrecios;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaPreciosRemoto;
/*   8:    */ import com.asinfo.as2.entities.DetalleVersionListaPrecios;
/*   9:    */ import com.asinfo.as2.entities.ListaPrecios;
/*  10:    */ import com.asinfo.as2.entities.VersionListaPrecios;
/*  11:    */ import com.asinfo.as2.enumeraciones.TipoListaPreciosEnum;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import java.math.BigDecimal;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.HashMap;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.ejb.Stateless;
/*  21:    */ 
/*  22:    */ @Stateless
/*  23:    */ public class ServicioListaPreciosImpl
/*  24:    */   implements ServicioListaPrecios, ServicioListaPreciosRemoto
/*  25:    */ {
/*  26:    */   @EJB
/*  27:    */   private ListaPreciosDao listaPreciosDao;
/*  28:    */   @EJB
/*  29:    */   private VersionListaPreciosDao versionListaPreciosDao;
/*  30:    */   @EJB
/*  31:    */   private DetalleVersionListaPreciosDao detalleVersionListaPreciosDao;
/*  32:    */   
/*  33:    */   public void guardar(ListaPrecios listaPrecios)
/*  34:    */   {
/*  35: 56 */     if (listaPrecios.isIndicadorCompra()) {
/*  36: 57 */       listaPrecios.setTipoListaPrecios(TipoListaPreciosEnum.LISTA_PRECIOS);
/*  37:    */     }
/*  38: 60 */     for (VersionListaPrecios versionListaPrecios : listaPrecios.getVersionesListaPrecios())
/*  39:    */     {
/*  40: 61 */       this.versionListaPreciosDao.guardar(versionListaPrecios);
/*  41: 62 */       for (DetalleVersionListaPrecios detalleVersionListaPrecios : versionListaPrecios.getDetalleVersionesListaPrecios()) {
/*  42: 63 */         this.detalleVersionListaPreciosDao.guardar(detalleVersionListaPrecios);
/*  43:    */       }
/*  44:    */     }
/*  45: 66 */     this.listaPreciosDao.guardar(listaPrecios);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void eliminar(ListaPrecios listaPrecios)
/*  49:    */   {
/*  50: 75 */     this.listaPreciosDao.eliminar(listaPrecios);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public ListaPrecios buscarPorId(int id)
/*  54:    */   {
/*  55: 85 */     return (ListaPrecios)this.listaPreciosDao.buscarPorId(Integer.valueOf(id));
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List<ListaPrecios> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  59:    */   {
/*  60: 96 */     return this.listaPreciosDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public List<ListaPrecios> obtenerListaCombo(String sortField, Map<String, String> filters)
/*  64:    */   {
/*  65:101 */     return obtenerListaCombo(sortField, true, filters);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public List<ListaPrecios> obtenerListaCombo()
/*  69:    */   {
/*  70:112 */     return obtenerListaCombo("codigo", null);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public DetalleVersionListaPrecios getDatosVersionListaPrecios(int idListaPrecios, int idProducto, Date fecha, Integer idZona, String numeroFactura)
/*  74:    */     throws ExcepcionAS2
/*  75:    */   {
/*  76:124 */     return this.listaPreciosDao.getDatosVersionListaPrecios(idListaPrecios, idProducto, fecha, idZona, numeroFactura, true);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public DetalleVersionListaPrecios getDatosVersionListaPrecios(int idListaPrecios, int idProducto, Date fecha, Integer idZona, String numeroFactura, boolean verificarZona)
/*  80:    */     throws ExcepcionAS2
/*  81:    */   {
/*  82:136 */     return this.listaPreciosDao.getDatosVersionListaPrecios(idListaPrecios, idProducto, fecha, idZona, numeroFactura, verificarZona);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public ListaPrecios cargarDetalle(int idListaPrecios)
/*  86:    */   {
/*  87:145 */     return this.listaPreciosDao.cargarDetalle(idListaPrecios);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public List<ListaPrecios> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  91:    */   {
/*  92:155 */     return this.listaPreciosDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int contarPorCriterio(Map<String, String> filters)
/*  96:    */   {
/*  97:165 */     return this.listaPreciosDao.contarPorCriterio(filters);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public DetalleVersionListaPrecios getDatosVersionListaPrecios(int idListaPrecios, int idProducto, Date fecha, Integer idZona)
/* 101:    */     throws ExcepcionAS2
/* 102:    */   {
/* 103:171 */     return this.listaPreciosDao.getDatosVersionListaPrecios(idListaPrecios, idProducto, fecha, idZona, "", true);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public ListaPrecios obtenerListaPreciosVigente(int idListaPrecios)
/* 107:    */   {
/* 108:181 */     return this.listaPreciosDao.obtenerListaPreciosVigente(idListaPrecios);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public List<VersionListaPrecios> getZonaListaPreciosProductoNuevo(int idOrganizacion, int idListaPrecios, int idProducto)
/* 112:    */   {
/* 113:191 */     return this.listaPreciosDao.getZonaListaPreciosProductoNuevo(idOrganizacion, idListaPrecios, idProducto);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void guardarListaDetalleVersionListaPrecios(List<DetalleVersionListaPrecios> listaDetalleVersionListaPrecios)
/* 117:    */   {
/* 118:201 */     for (DetalleVersionListaPrecios detalleVersionListaPrecios : listaDetalleVersionListaPrecios) {
/* 119:202 */       this.detalleVersionListaPreciosDao.guardar(detalleVersionListaPrecios);
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   public List getReporteListaPrecios(int idVersionListaPrecios)
/* 124:    */   {
/* 125:215 */     return this.listaPreciosDao.getReporteListaPreciosUltimaVersion(idVersionListaPrecios);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public VersionListaPrecios getUltimaVersionListaPrecios(int idListaPrecios)
/* 129:    */   {
/* 130:226 */     return this.listaPreciosDao.getUltimaVersionListaPrecios(idListaPrecios);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public List<ListaPrecios> autocompletarListaPrecios(String consulta)
/* 134:    */   {
/* 135:231 */     return autocompletarListaPrecios(consulta, false, 0);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public List<ListaPrecios> autocompletarListaPrecios(String consulta, boolean listaPreciosDeCompra, int idOrganizacion)
/* 139:    */   {
/* 140:236 */     List<ListaPrecios> lista = new ArrayList();
/* 141:    */     
/* 142:238 */     String sortField = "codigo";
/* 143:239 */     HashMap<String, String> filters = new HashMap();
/* 144:240 */     filters.put("nombre", consulta.trim());
/* 145:241 */     if (listaPreciosDeCompra) {
/* 146:242 */       filters.put("indicadorCompra", String.valueOf(true));
/* 147:    */     }
/* 148:244 */     if (idOrganizacion != 0) {
/* 149:245 */       filters.put("idOrganizacion", String.valueOf(idOrganizacion));
/* 150:    */     }
/* 151:247 */     lista = this.listaPreciosDao.obtenerListaCombo(sortField, true, filters);
/* 152:    */     
/* 153:249 */     return lista;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public List<VersionListaPrecios> getListaVersionListaPrecios(ListaPrecios listaPrecios)
/* 157:    */   {
/* 158:255 */     return this.listaPreciosDao.getListaVersionListaPrecios(listaPrecios);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public BigDecimal precioProducto(VersionListaPrecios versionListaPrecios, String nombreProducto)
/* 162:    */   {
/* 163:260 */     return this.listaPreciosDao.precioProducto(versionListaPrecios, nombreProducto);
/* 164:    */   }
/* 165:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.impl.ServicioListaPreciosImpl
 * JD-Core Version:    0.7.0.1
 */