/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.OneToMany;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ import javax.validation.constraints.Size;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="actividad_mantenimiento", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  23:    */ public class ActividadMantenimiento
/*  24:    */   extends EntidadBase
/*  25:    */   implements Serializable
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="actividad", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="actividad")
/*  31:    */   @Column(name="id_actividad_mantenimiento")
/*  32:    */   private int idActividadMantenimiento;
/*  33:    */   @Column(name="id_organizacion")
/*  34:    */   private int idOrganizacion;
/*  35:    */   @Column(name="id_sucursal")
/*  36:    */   private int idSucursal;
/*  37:    */   @Column(name="codigo", nullable=false, length=20)
/*  38:    */   @NotNull
/*  39:    */   @Size(min=1, max=20)
/*  40:    */   private String codigo;
/*  41:    */   @Column(name="nombre", nullable=false, length=100)
/*  42:    */   @NotNull
/*  43:    */   @Size(min=2, max=100)
/*  44:    */   private String nombre;
/*  45:    */   @Column(name="descripcion", length=200, nullable=true)
/*  46:    */   @Size(max=200)
/*  47:    */   private String descripcion;
/*  48:    */   @Column(name="predeterminado", nullable=false)
/*  49:    */   private boolean predeterminado;
/*  50:    */   @Column(name="activo", nullable=false)
/*  51:    */   private boolean activo;
/*  52:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  53:    */   @JoinColumn(name="id_tipo_actividad", nullable=false)
/*  54:    */   private TipoActividad tipoActividad;
/*  55:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="actividadMantenimiento")
/*  56: 76 */   private List<ActividadMantenimientoHerramienta> listaHerramienta = new ArrayList();
/*  57:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="actividadMantenimiento")
/*  58: 79 */   private List<ActividadMantenimientoMaterial> listaMaterial = new ArrayList();
/*  59:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="actividadMantenimiento")
/*  60: 82 */   private List<ActividadMantenimientoEspecialidad> listaEspecialidad = new ArrayList();
/*  61:    */   
/*  62:    */   public int getId()
/*  63:    */   {
/*  64: 95 */     return this.idActividadMantenimiento;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int getIdActividadMantenimiento()
/*  68:    */   {
/*  69:102 */     return this.idActividadMantenimiento;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setIdActividadMantenimiento(int idActividadMantenimiento)
/*  73:    */   {
/*  74:110 */     this.idActividadMantenimiento = idActividadMantenimiento;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public int getIdOrganizacion()
/*  78:    */   {
/*  79:117 */     return this.idOrganizacion;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setIdOrganizacion(int idOrganizacion)
/*  83:    */   {
/*  84:125 */     this.idOrganizacion = idOrganizacion;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getIdSucursal()
/*  88:    */   {
/*  89:132 */     return this.idSucursal;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setIdSucursal(int idSucursal)
/*  93:    */   {
/*  94:140 */     this.idSucursal = idSucursal;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String getCodigo()
/*  98:    */   {
/*  99:147 */     return this.codigo;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setCodigo(String codigo)
/* 103:    */   {
/* 104:155 */     this.codigo = codigo;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getNombre()
/* 108:    */   {
/* 109:162 */     return this.nombre;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setNombre(String nombre)
/* 113:    */   {
/* 114:170 */     this.nombre = nombre;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String getDescripcion()
/* 118:    */   {
/* 119:177 */     return this.descripcion;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setDescripcion(String descripcion)
/* 123:    */   {
/* 124:185 */     this.descripcion = descripcion;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean isPredeterminado()
/* 128:    */   {
/* 129:192 */     return this.predeterminado;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setPredeterminado(boolean predeterminado)
/* 133:    */   {
/* 134:200 */     this.predeterminado = predeterminado;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public boolean isActivo()
/* 138:    */   {
/* 139:207 */     return this.activo;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setActivo(boolean activo)
/* 143:    */   {
/* 144:215 */     this.activo = activo;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public TipoActividad getTipoActividad()
/* 148:    */   {
/* 149:222 */     return this.tipoActividad;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setTipoActividad(TipoActividad tipoActividad)
/* 153:    */   {
/* 154:230 */     this.tipoActividad = tipoActividad;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public List<ActividadMantenimientoHerramienta> getListaHerramienta()
/* 158:    */   {
/* 159:234 */     return this.listaHerramienta;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setListaHerramienta(List<ActividadMantenimientoHerramienta> listaHerramienta)
/* 163:    */   {
/* 164:238 */     this.listaHerramienta = listaHerramienta;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public List<ActividadMantenimientoMaterial> getListaMaterial()
/* 168:    */   {
/* 169:242 */     return this.listaMaterial;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setListaMaterial(List<ActividadMantenimientoMaterial> listaMaterial)
/* 173:    */   {
/* 174:246 */     this.listaMaterial = listaMaterial;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public List<ActividadMantenimientoEspecialidad> getListaEspecialidad()
/* 178:    */   {
/* 179:250 */     return this.listaEspecialidad;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setListaEspecialidad(List<ActividadMantenimientoEspecialidad> listaEspecialidad)
/* 183:    */   {
/* 184:254 */     this.listaEspecialidad = listaEspecialidad;
/* 185:    */   }
/* 186:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento
 * JD-Core Version:    0.7.0.1
 */