/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Dispositivo;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   9:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import com.asinfo.as2.utils.JsfUtil;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.annotation.PostConstruct;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ import org.primefaces.component.datatable.DataTable;
/*  21:    */ import org.primefaces.model.LazyDataModel;
/*  22:    */ import org.primefaces.model.SortOrder;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class DispositivoBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @EJB
/*  31:    */   private ServicioGenerico<Dispositivo> servicioDispositivo;
/*  32:    */   private Dispositivo dispositivo;
/*  33:    */   private LazyDataModel<Dispositivo> listaDispositivo;
/*  34:    */   private DataTable dataTableDispositivo;
/*  35:    */   
/*  36:    */   @PostConstruct
/*  37:    */   public void init()
/*  38:    */   {
/*  39: 69 */     this.listaDispositivo = new LazyDataModel()
/*  40:    */     {
/*  41:    */       private static final long serialVersionUID = -1956844755215090557L;
/*  42:    */       
/*  43:    */       public List<Dispositivo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  44:    */       {
/*  45: 75 */         List<Dispositivo> lista = new ArrayList();
/*  46: 76 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  47: 77 */         DispositivoBean.this.agregarFiltroOrganizacion(filters);
/*  48:    */         try
/*  49:    */         {
/*  50: 79 */           lista = DispositivoBean.this.servicioDispositivo.obtenerListaPorPagina(Dispositivo.class, startIndex, pageSize, sortField, ordenar, filters);
/*  51:    */         }
/*  52:    */         catch (Exception e)
/*  53:    */         {
/*  54: 82 */           e.printStackTrace();
/*  55:    */         }
/*  56: 84 */         DispositivoBean.this.listaDispositivo.setRowCount(DispositivoBean.this.servicioDispositivo.contarPorCriterio(Dispositivo.class, filters));
/*  57: 85 */         return lista;
/*  58:    */       }
/*  59:    */     };
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String editar()
/*  63:    */   {
/*  64: 97 */     if ((this.dispositivo != null) && (this.dispositivo.getIdDispositivo() > 0)) {
/*  65: 98 */       setEditado(true);
/*  66:    */     } else {
/*  67:100 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  68:    */     }
/*  69:103 */     return "";
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String guardar()
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76:114 */       this.servicioDispositivo.guardarValidar(this.dispositivo);
/*  77:115 */       cargarDatos();
/*  78:    */       
/*  79:117 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  80:    */     }
/*  81:    */     catch (AS2Exception e)
/*  82:    */     {
/*  83:119 */       JsfUtil.addErrorMessage(e, "");
/*  84:    */     }
/*  85:    */     catch (Exception e)
/*  86:    */     {
/*  87:121 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  88:122 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  89:    */     }
/*  90:124 */     return "";
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String eliminar()
/*  94:    */   {
/*  95:134 */     if ((this.dispositivo != null) && (this.dispositivo.getIdDispositivo() > 0)) {
/*  96:    */       try
/*  97:    */       {
/*  98:136 */         this.servicioDispositivo.eliminar(this.dispositivo);
/*  99:137 */         cargarDatos();
/* 100:    */         
/* 101:139 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 102:    */       }
/* 103:    */       catch (Exception e)
/* 104:    */       {
/* 105:141 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 106:142 */         LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 107:    */       }
/* 108:    */     } else {
/* 109:145 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 110:    */     }
/* 111:147 */     return "";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String cargarDatos()
/* 115:    */   {
/* 116:158 */     setEditado(false);
/* 117:    */     try
/* 118:    */     {
/* 119:161 */       limpiar();
/* 120:    */     }
/* 121:    */     catch (Exception e)
/* 122:    */     {
/* 123:164 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 124:165 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 125:    */     }
/* 126:167 */     return "";
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String limpiar()
/* 130:    */   {
/* 131:177 */     this.dispositivo = new Dispositivo();
/* 132:178 */     this.dispositivo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 133:179 */     this.dispositivo.setIdSucursal(AppUtil.getSucursal().getId());
/* 134:180 */     return "";
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Dispositivo getDispositivo()
/* 138:    */   {
/* 139:189 */     return this.dispositivo;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setDispositivo(Dispositivo dispositivo)
/* 143:    */   {
/* 144:199 */     this.dispositivo = dispositivo;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public DataTable getDataTableDispositivo()
/* 148:    */   {
/* 149:208 */     return this.dataTableDispositivo;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setDataTableDispositivo(DataTable dataTableDispositivo)
/* 153:    */   {
/* 154:212 */     this.dataTableDispositivo = dataTableDispositivo;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public LazyDataModel<Dispositivo> getListaDispositivo()
/* 158:    */   {
/* 159:216 */     if (this.listaDispositivo == null) {
/* 160:217 */       cargarDatos();
/* 161:    */     }
/* 162:219 */     return this.listaDispositivo;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setListaDispositivo(LazyDataModel<Dispositivo> listaDispositivo)
/* 166:    */   {
/* 167:223 */     this.listaDispositivo = listaDispositivo;
/* 168:    */   }
/* 169:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.DispositivoBean
 * JD-Core Version:    0.7.0.1
 */