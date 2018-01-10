/*   1:    */ package com.asinfo.as2.datosbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.GenericoDao;
/*   4:    */ import com.asinfo.as2.dao.ListaDescuentosDao;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioListaDescuentos;
/*   6:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   7:    */ import com.asinfo.as2.entities.DetalleListaDescuentos;
/*   8:    */ import com.asinfo.as2.entities.ListaDescuentos;
/*   9:    */ import com.asinfo.as2.entities.Producto;
/*  10:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  11:    */ import com.asinfo.as2.entities.VersionListaDescuentos;
/*  12:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  13:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  14:    */ import java.math.BigDecimal;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.HashMap;
/*  17:    */ import java.util.Iterator;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.ejb.Stateless;
/*  22:    */ 
/*  23:    */ @Stateless
/*  24:    */ public class ServicioListaDescuentosImpl
/*  25:    */   implements ServicioListaDescuentos
/*  26:    */ {
/*  27:    */   @EJB
/*  28:    */   private ListaDescuentosDao listaDescuentosDao;
/*  29:    */   @EJB
/*  30:    */   private GenericoDao<DetalleListaDescuentos> detalleListaDescuentosDao;
/*  31:    */   @EJB
/*  32:    */   private GenericoDao<VersionListaDescuentos> versionListaDescuentosDao;
/*  33:    */   
/*  34:    */   public void guardar(ListaDescuentos listaDescuentos)
/*  35:    */     throws AS2Exception
/*  36:    */   {
/*  37: 56 */     validar(listaDescuentos);
/*  38: 57 */     for (VersionListaDescuentos vld : listaDescuentos.getListaVersionesListaDescuentos())
/*  39:    */     {
/*  40: 58 */       this.versionListaDescuentosDao.guardar(vld);
/*  41: 59 */       for (DetalleListaDescuentos detalleListaDescuentos : vld.getListaDetalleListaDescuentos()) {
/*  42: 60 */         this.detalleListaDescuentosDao.guardar(detalleListaDescuentos);
/*  43:    */       }
/*  44:    */     }
/*  45: 63 */     this.listaDescuentosDao.guardar(listaDescuentos);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void eliminar(ListaDescuentos listaDescuentos)
/*  49:    */   {
/*  50: 72 */     this.listaDescuentosDao.eliminar(listaDescuentos);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public ListaDescuentos buscarPorId(int id)
/*  54:    */   {
/*  55: 82 */     return (ListaDescuentos)this.listaDescuentosDao.buscarPorId(Integer.valueOf(id));
/*  56:    */   }
/*  57:    */   
/*  58:    */   public List<ListaDescuentos> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  59:    */   {
/*  60: 94 */     return this.listaDescuentosDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public DetalleListaDescuentos getDatosListaDescuentosPorProducto(ListaDescuentos listaDescuentos, Producto producto)
/*  64:    */   {
/*  65:104 */     return this.listaDescuentosDao.getDatosListaDescuentosPorProducto(listaDescuentos, producto);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public ListaDescuentos cargarDetalle(int idListaDescuentos)
/*  69:    */   {
/*  70:114 */     return this.listaDescuentosDao.cargarDetalle(idListaDescuentos);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public List<ListaDescuentos> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  74:    */   {
/*  75:125 */     return this.listaDescuentosDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int contarPorCriterio(Map<String, String> filters)
/*  79:    */   {
/*  80:135 */     return this.listaDescuentosDao.contarPorCriterio(filters);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public List<Object[]> getReportelistaDescuentos(int idListaDescuentos)
/*  84:    */   {
/*  85:140 */     return this.listaDescuentosDao.getReportelistaDescuentos(idListaDescuentos);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public List<DetalleListaDescuentos> listaCambioMasivoDescuento(List<ListaDescuentos> listaListaDescuentos, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto)
/*  89:    */   {
/*  90:146 */     return this.listaDescuentosDao.listaCambioMasivoDescuento(listaListaDescuentos, categoriaProducto, subcategoriaProducto, producto);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void cambioMasivoDescuento(List<DetalleListaDescuentos> detalleListaListaDescuentos, BigDecimal porcentajeDescuento)
/*  94:    */     throws AS2Exception
/*  95:    */   {
/*  96:153 */     if ((detalleListaListaDescuentos.size() == 0) || (detalleListaListaDescuentos == null)) {
/*  97:154 */       throw new AS2Exception("com.asinfo.as2.datosbase.servicio.impl.ServicioListaDescuentosImpl.REGISTRO_NO_ENCONTRADO_LISTA_DESCUENTO", new String[] { "" });
/*  98:    */     }
/*  99:157 */     this.listaDescuentosDao.cambioMasivoDescuento(detalleListaListaDescuentos, porcentajeDescuento);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void asignarProductoNoAsignados(List<ListaDescuentos> listadoListaDescuentos, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, BigDecimal porcentajeDescuento)
/* 103:    */     throws AS2Exception
/* 104:    */   {
/* 105:165 */     for (Iterator localIterator1 = listadoListaDescuentos.iterator(); localIterator1.hasNext();)
/* 106:    */     {
/* 107:165 */       listaDescuentos = (ListaDescuentos)localIterator1.next();
/* 108:166 */       List<Producto> productos = this.listaDescuentosDao.obtenerProductoNoAsignados(listaDescuentos, categoriaProducto, subcategoriaProducto, producto);
/* 109:    */       
/* 110:    */ 
/* 111:169 */       HashMap<String, String> filtros = new HashMap();
/* 112:170 */       filtros.put("listaDescuentos.idListaDescuentos", "=" + listaDescuentos.getId());
/* 113:171 */       filtros.put("activo", "=true");
/* 114:172 */       listaVersionListaDescuentos = this.versionListaDescuentosDao.obtenerListaCombo(VersionListaDescuentos.class, "", true, filtros);
/* 115:175 */       if (listaVersionListaDescuentos.isEmpty()) {
/* 116:176 */         throw new AS2Exception("com.asinfo.as2.datosbase.servicio.impl.ServicioListaDescuentosImpl.NO_EXISTE_VERSION_LISTA_DESCUENTO_ACTIVO", new String[] { listaDescuentos.getNombre() });
/* 117:    */       }
/* 118:179 */       for (Producto productoAdd : productos)
/* 119:    */       {
/* 120:180 */         DetalleListaDescuentos detalleListaDescuentos = new DetalleListaDescuentos();
/* 121:181 */         detalleListaDescuentos.setIdOrganizacion(listaDescuentos.getIdOrganizacion());
/* 122:182 */         detalleListaDescuentos.setIdSucursal(listaDescuentos.getIdSucursal());
/* 123:183 */         detalleListaDescuentos.setVersionListaDescuentos((VersionListaDescuentos)listaVersionListaDescuentos.get(0));
/* 124:184 */         detalleListaDescuentos.setProducto(productoAdd);
/* 125:185 */         detalleListaDescuentos.setPorcentajeDescuentoMaximo(porcentajeDescuento);
/* 126:    */         
/* 127:187 */         this.detalleListaDescuentosDao.guardar(detalleListaDescuentos);
/* 128:    */       }
/* 129:    */     }
/* 130:    */     ListaDescuentos listaDescuentos;
/* 131:    */     List<VersionListaDescuentos> listaVersionListaDescuentos;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public ListaDescuentos obtenerListaDescuentosVigente(int idListaDescuentos)
/* 135:    */   {
/* 136:196 */     return this.listaDescuentosDao.obtenerListaDescuentosVigente(idListaDescuentos);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void validar(ListaDescuentos lista)
/* 140:    */     throws AS2Exception
/* 141:    */   {
/* 142:204 */     List<VersionListaDescuentos> listaView = lista.getListaVersionListaDescuentosView();
/* 143:    */     
/* 144:206 */     Date[][] fechas = new Date[listaView.size()][2];
/* 145:208 */     for (int i = 0; i < listaView.size(); i++)
/* 146:    */     {
/* 147:209 */       fechas[i][0] = ((VersionListaDescuentos)listaView.get(i)).getValidoDesde();
/* 148:210 */       fechas[i][1] = (((VersionListaDescuentos)listaView.get(i)).getValidoHasta() == null ? FuncionesUtiles.getFecha(12, 12, 9999) : ((VersionListaDescuentos)listaView.get(i)).getValidoHasta());
/* 149:    */     }
/* 150:213 */     FuncionesUtiles.validarPeriodosExcluyentes(fechas);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public BigDecimal getPorcentajeDescuentoMaximoVigente(ListaDescuentos listaDescuentos, Date fecha)
/* 154:    */   {
/* 155:219 */     return this.listaDescuentosDao.getPorcentajeDescuentoMaximoVigente(listaDescuentos, fecha);
/* 156:    */   }
/* 157:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.impl.ServicioListaDescuentosImpl
 * JD-Core Version:    0.7.0.1
 */