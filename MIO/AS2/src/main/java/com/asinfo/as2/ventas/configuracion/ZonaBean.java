/*   1:    */ package com.asinfo.as2.ventas.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sector;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.Zona;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.annotation.PostConstruct;
/*  15:    */ import javax.ejb.EJB;
/*  16:    */ import javax.faces.bean.ManagedBean;
/*  17:    */ import javax.faces.bean.ViewScoped;
/*  18:    */ import org.apache.log4j.Logger;
/*  19:    */ import org.primefaces.component.datatable.DataTable;
/*  20:    */ import org.primefaces.model.LazyDataModel;
/*  21:    */ import org.primefaces.model.SortOrder;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class ZonaBean
/*  26:    */   extends PageControllerAS2
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @EJB
/*  30:    */   private ServicioZona servicioZona;
/*  31:    */   private Zona zona;
/*  32:    */   private List<Sector> listaSector;
/*  33:    */   private LazyDataModel<Zona> listaZona;
/*  34:    */   private DataTable dtZona;
/*  35:    */   private DataTable dtSector;
/*  36:    */   
/*  37:    */   @PostConstruct
/*  38:    */   public void init()
/*  39:    */   {
/*  40: 77 */     this.listaZona = new LazyDataModel()
/*  41:    */     {
/*  42:    */       private static final long serialVersionUID = 1L;
/*  43:    */       
/*  44:    */       public List<Zona> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  45:    */       {
/*  46: 84 */         List<Zona> lista = new ArrayList();
/*  47: 85 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  48:    */         
/*  49: 87 */         lista = ZonaBean.this.servicioZona.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  50:    */         
/*  51: 89 */         ZonaBean.this.listaZona.setRowCount(ZonaBean.this.servicioZona.contarPorCriterio(filters));
/*  52:    */         
/*  53: 91 */         return lista;
/*  54:    */       }
/*  55:    */     };
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void agregarDetalle()
/*  59:    */   {
/*  60: 99 */     Sector sector = new Sector();
/*  61:100 */     sector.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  62:101 */     sector.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  63:102 */     sector.setZona(getZona());
/*  64:103 */     getZona().getListaSector().add(sector);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String eliminarDetalle()
/*  68:    */   {
/*  69:109 */     Sector s = (Sector)this.dtSector.getRowData();
/*  70:110 */     s.setEliminado(true);
/*  71:111 */     return "";
/*  72:    */   }
/*  73:    */   
/*  74:    */   public List<Sector> getListaSectorNoEliminados()
/*  75:    */   {
/*  76:116 */     List<Sector> lista = new ArrayList();
/*  77:118 */     for (Sector pc : getZona().getListaSector()) {
/*  78:119 */       if (!pc.isEliminado()) {
/*  79:120 */         lista.add(pc);
/*  80:    */       }
/*  81:    */     }
/*  82:124 */     return lista;
/*  83:    */   }
/*  84:    */   
/*  85:    */   private void crearZona()
/*  86:    */   {
/*  87:132 */     this.zona = new Zona();
/*  88:133 */     this.zona.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  89:134 */     this.zona.setIdSucursal(AppUtil.getSucursal().getId());
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String editar()
/*  93:    */   {
/*  94:143 */     if (getZona().getIdZona() != 0)
/*  95:    */     {
/*  96:144 */       setEditado(true);
/*  97:145 */       setZona(this.servicioZona.cargarDetalle(getZona().getId()));
/*  98:    */     }
/*  99:    */     else
/* 100:    */     {
/* 101:147 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 102:    */     }
/* 103:149 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String guardar()
/* 107:    */   {
/* 108:    */     try
/* 109:    */     {
/* 110:159 */       this.servicioZona.guardar(this.zona);
/* 111:160 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 112:161 */       setEditado(false);
/* 113:162 */       limpiar();
/* 114:    */     }
/* 115:    */     catch (Exception e)
/* 116:    */     {
/* 117:164 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 118:165 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 119:    */     }
/* 120:167 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String eliminar()
/* 124:    */   {
/* 125:    */     try
/* 126:    */     {
/* 127:177 */       this.servicioZona.eliminar(this.zona);
/* 128:178 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 129:    */     }
/* 130:    */     catch (Exception e)
/* 131:    */     {
/* 132:180 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 133:181 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 134:    */     }
/* 135:183 */     return "";
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String cargarDatos()
/* 139:    */   {
/* 140:192 */     return "";
/* 141:    */   }
/* 142:    */   
/* 143:    */   public String limpiar()
/* 144:    */   {
/* 145:201 */     crearZona();
/* 146:202 */     return "";
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Zona getZona()
/* 150:    */   {
/* 151:219 */     if (this.zona == null) {
/* 152:220 */       crearZona();
/* 153:    */     }
/* 154:222 */     return this.zona;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setZona(Zona zona)
/* 158:    */   {
/* 159:232 */     this.zona = zona;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public LazyDataModel<Zona> getListaZona()
/* 163:    */   {
/* 164:241 */     return this.listaZona;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setListaZona(LazyDataModel<Zona> listaZona)
/* 168:    */   {
/* 169:251 */     this.listaZona = listaZona;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public DataTable getDtZona()
/* 173:    */   {
/* 174:260 */     return this.dtZona;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setDtZona(DataTable dtZona)
/* 178:    */   {
/* 179:270 */     this.dtZona = dtZona;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public DataTable getDtSector()
/* 183:    */   {
/* 184:274 */     return this.dtSector;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setDtSector(DataTable dtSector)
/* 188:    */   {
/* 189:278 */     this.dtSector = dtSector;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public List<Sector> getListaSector()
/* 193:    */   {
/* 194:282 */     if (this.listaSector == null) {
/* 195:283 */       this.listaSector = new ArrayList();
/* 196:    */     }
/* 197:285 */     return this.listaSector;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setListaSector(List<Sector> listaSector)
/* 201:    */   {
/* 202:289 */     this.listaSector = listaSector;
/* 203:    */   }
/* 204:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.ZonaBean
 * JD-Core Version:    0.7.0.1
 */