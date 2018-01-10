/*   1:    */ package com.asinfo.as2.entities.mantenimiento.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.OneToMany;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="actividad")
/*  22:    */ public class Actividad
/*  23:    */   extends EntidadBase
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = -6264401803132998147L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="actividad", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="actividad")
/*  29:    */   @Column(name="id_actividad")
/*  30:    */   private int idActividad;
/*  31:    */   @Column(name="id_organizacion", nullable=false)
/*  32:    */   private int idOrganizacion;
/*  33:    */   @Column(name="id_sucursal", nullable=false)
/*  34:    */   private int idSucursal;
/*  35:    */   @Column(name="codigo", nullable=false, length=10)
/*  36:    */   @NotNull
/*  37:    */   @Size(min=2, max=10)
/*  38:    */   private String codigo;
/*  39:    */   @Column(name="nombre", nullable=false, length=50)
/*  40:    */   @NotNull
/*  41:    */   @Size(min=2, max=50)
/*  42:    */   private String nombre;
/*  43:    */   @Column(name="descripcion", length=200)
/*  44:    */   @Size(max=200)
/*  45:    */   private String descripcion;
/*  46:    */   @Column(name="activo", nullable=false)
/*  47:    */   private boolean activo;
/*  48:    */   @Column(name="predeterminado", nullable=false)
/*  49:    */   private boolean predeterminado;
/*  50:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  51:    */   @JoinColumn(name="id_criticidad", nullable=false)
/*  52:    */   @NotNull
/*  53:    */   private Criticidad criticidad;
/*  54:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_lista_verificacion", nullable=true)
/*  56:    */   private ListaVerificacion listaVerificacion;
/*  57:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="actividad")
/*  58: 96 */   private List<Tarea> listaTarea = new ArrayList();
/*  59:    */   
/*  60:    */   public int getIdActividad()
/*  61:    */   {
/*  62:122 */     return this.idActividad;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdActividad(int idActividad)
/*  66:    */   {
/*  67:132 */     this.idActividad = idActividad;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdOrganizacion()
/*  71:    */   {
/*  72:141 */     return this.idOrganizacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdOrganizacion(int idOrganizacion)
/*  76:    */   {
/*  77:151 */     this.idOrganizacion = idOrganizacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdSucursal()
/*  81:    */   {
/*  82:160 */     return this.idSucursal;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdSucursal(int idSucursal)
/*  86:    */   {
/*  87:170 */     this.idSucursal = idSucursal;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getCodigo()
/*  91:    */   {
/*  92:179 */     return this.codigo;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setCodigo(String codigo)
/*  96:    */   {
/*  97:189 */     this.codigo = codigo;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getNombre()
/* 101:    */   {
/* 102:198 */     return this.nombre;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setNombre(String nombre)
/* 106:    */   {
/* 107:208 */     this.nombre = nombre;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getDescripcion()
/* 111:    */   {
/* 112:217 */     return this.descripcion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setDescripcion(String descripcion)
/* 116:    */   {
/* 117:227 */     this.descripcion = descripcion;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Criticidad getCriticidad()
/* 121:    */   {
/* 122:236 */     return this.criticidad;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setCriticidad(Criticidad criticidad)
/* 126:    */   {
/* 127:246 */     this.criticidad = criticidad;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public ListaVerificacion getListaVerificacion()
/* 131:    */   {
/* 132:255 */     return this.listaVerificacion;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setListaVerificacion(ListaVerificacion listaVerificacion)
/* 136:    */   {
/* 137:265 */     this.listaVerificacion = listaVerificacion;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public List<Tarea> getListaTarea()
/* 141:    */   {
/* 142:274 */     return this.listaTarea;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setListaTarea(List<Tarea> listaTarea)
/* 146:    */   {
/* 147:284 */     this.listaTarea = listaTarea;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean isActivo()
/* 151:    */   {
/* 152:293 */     return this.activo;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setActivo(boolean activo)
/* 156:    */   {
/* 157:303 */     this.activo = activo;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public boolean isPredeterminado()
/* 161:    */   {
/* 162:312 */     return this.predeterminado;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setPredeterminado(boolean predeterminado)
/* 166:    */   {
/* 167:322 */     this.predeterminado = predeterminado;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public int getId()
/* 171:    */   {
/* 172:332 */     return this.idActividad;
/* 173:    */   }
/* 174:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.old.Actividad
 * JD-Core Version:    0.7.0.1
 */