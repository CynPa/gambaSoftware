/*   1:    */ package com.asinfo.as2.mantenimiento.procesos.controller.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.mantenimiento.old.ArticuloServicio;
/*   7:    */ import com.asinfo.as2.entities.mantenimiento.old.HistoricoArticuloServicio;
/*   8:    */ import com.asinfo.as2.mantenimiento.procesos.old.ServicioArticuloServicio;
/*   9:    */ import com.asinfo.as2.mantenimiento.procesos.old.ServicioHistoricoArticuloServicio;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.annotation.PostConstruct;
/*  15:    */ import javax.ejb.EJB;
/*  16:    */ import javax.faces.bean.ManagedBean;
/*  17:    */ import javax.faces.bean.ViewScoped;
/*  18:    */ import org.apache.log4j.Logger;
/*  19:    */ import org.primefaces.component.datatable.DataTableRenderer;
/*  20:    */ import org.primefaces.model.LazyDataModel;
/*  21:    */ import org.primefaces.model.SortOrder;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class MovimientoArticuloServicioBean
/*  26:    */   extends PageControllerAS2
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -1487710286401386918L;
/*  29:    */   @EJB
/*  30:    */   private ServicioHistoricoArticuloServicio servicioHistoricoArticuloServicio;
/*  31:    */   @EJB
/*  32:    */   private ServicioArticuloServicio servicioArticuloServicio;
/*  33:    */   private HistoricoArticuloServicio historicoArticuloServicio;
/*  34:    */   private ArticuloServicio articuloServicio;
/*  35:    */   private LazyDataModel<HistoricoArticuloServicio> listaHistoricoArticuloServicio;
/*  36:    */   private DataTableRenderer dtHistoricoArticuloServicio;
/*  37:    */   
/*  38:    */   @PostConstruct
/*  39:    */   public void init()
/*  40:    */   {
/*  41: 81 */     this.listaHistoricoArticuloServicio = new LazyDataModel()
/*  42:    */     {
/*  43:    */       private static final long serialVersionUID = -8347504588236883783L;
/*  44:    */       
/*  45:    */       public List<HistoricoArticuloServicio> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  46:    */       {
/*  47: 92 */         List<HistoricoArticuloServicio> lista = new ArrayList();
/*  48: 93 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  49:    */         
/*  50: 95 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  51: 96 */         lista = MovimientoArticuloServicioBean.this.servicioHistoricoArticuloServicio.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  52:    */         
/*  53: 98 */         MovimientoArticuloServicioBean.this.listaHistoricoArticuloServicio.setRowCount(MovimientoArticuloServicioBean.this.servicioHistoricoArticuloServicio.contarPorCriterio(filters));
/*  54:    */         
/*  55:100 */         return lista;
/*  56:    */       }
/*  57:    */     };
/*  58:    */   }
/*  59:    */   
/*  60:    */   private void crearEntidad()
/*  61:    */   {
/*  62:113 */     this.historicoArticuloServicio = new HistoricoArticuloServicio();
/*  63:114 */     this.historicoArticuloServicio.setArticuloServicioHijo(new ArticuloServicio());
/*  64:115 */     this.historicoArticuloServicio.setArticuloServicioPadre(new ArticuloServicio());
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String editar()
/*  68:    */   {
/*  69:124 */     if (getHistoricoArticuloServicio().getIdHistoricoServicio() > 0) {
/*  70:125 */       setEditado(true);
/*  71:    */     } else {
/*  72:127 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  73:    */     }
/*  74:129 */     return "";
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String guardar()
/*  78:    */   {
/*  79:    */     try
/*  80:    */     {
/*  81:139 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  82:140 */       setEditado(false);
/*  83:141 */       limpiar();
/*  84:    */     }
/*  85:    */     catch (Exception e)
/*  86:    */     {
/*  87:143 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  88:144 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  89:    */     }
/*  90:146 */     return "";
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String eliminar()
/*  94:    */   {
/*  95:    */     try
/*  96:    */     {
/*  97:157 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  98:    */     }
/*  99:    */     catch (Exception e)
/* 100:    */     {
/* 101:159 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 102:160 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 103:    */     }
/* 104:162 */     return "";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String cargarDatos()
/* 108:    */   {
/* 109:171 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String limpiar()
/* 113:    */   {
/* 114:180 */     crearEntidad();
/* 115:181 */     return "";
/* 116:    */   }
/* 117:    */   
/* 118:    */   public List<ArticuloServicio> autocompletarArticuloServicio(String consulta)
/* 119:    */   {
/* 120:185 */     List<ArticuloServicio> lista = this.servicioArticuloServicio.autocompletarArticuloServicio(consulta);
/* 121:186 */     return lista;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public ServicioHistoricoArticuloServicio getServicioHistoricoArticuloServicio()
/* 125:    */   {
/* 126:199 */     return this.servicioHistoricoArticuloServicio;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setServicioHistoricoArticuloServicio(ServicioHistoricoArticuloServicio servicioHistoricoArticuloServicio)
/* 130:    */   {
/* 131:209 */     this.servicioHistoricoArticuloServicio = servicioHistoricoArticuloServicio;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public ServicioArticuloServicio getServicioArticuloServicio()
/* 135:    */   {
/* 136:218 */     return this.servicioArticuloServicio;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setServicioArticuloServicio(ServicioArticuloServicio servicioArticuloServicio)
/* 140:    */   {
/* 141:228 */     this.servicioArticuloServicio = servicioArticuloServicio;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public HistoricoArticuloServicio getHistoricoArticuloServicio()
/* 145:    */   {
/* 146:237 */     return this.historicoArticuloServicio;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setHistoricoArticuloServicio(HistoricoArticuloServicio historicoArticuloServicio)
/* 150:    */   {
/* 151:247 */     this.historicoArticuloServicio = historicoArticuloServicio;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public ArticuloServicio getArticuloServicio()
/* 155:    */   {
/* 156:256 */     return this.articuloServicio;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setArticuloServicio(ArticuloServicio articuloServicio)
/* 160:    */   {
/* 161:266 */     this.articuloServicio = articuloServicio;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public LazyDataModel<HistoricoArticuloServicio> getListaHistoricoArticuloServicio()
/* 165:    */   {
/* 166:275 */     return this.listaHistoricoArticuloServicio;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setListaHistoricoArticuloServicio(LazyDataModel<HistoricoArticuloServicio> listaHistoricoArticuloServicio)
/* 170:    */   {
/* 171:285 */     this.listaHistoricoArticuloServicio = listaHistoricoArticuloServicio;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public DataTableRenderer getDtHistoricoArticuloServicio()
/* 175:    */   {
/* 176:294 */     return this.dtHistoricoArticuloServicio;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setDtHistoricoArticuloServicio(DataTableRenderer dtHistoricoArticuloServicio)
/* 180:    */   {
/* 181:304 */     this.dtHistoricoArticuloServicio = dtHistoricoArticuloServicio;
/* 182:    */   }
/* 183:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.procesos.controller.old.MovimientoArticuloServicioBean
 * JD-Core Version:    0.7.0.1
 */