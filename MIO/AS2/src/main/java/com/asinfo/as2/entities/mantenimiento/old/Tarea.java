/*   1:    */ package com.asinfo.as2.entities.mantenimiento.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.Max;
/*  15:    */ import javax.validation.constraints.Min;
/*  16:    */ import javax.validation.constraints.NotNull;
/*  17:    */ import javax.validation.constraints.Size;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="tarea")
/*  21:    */ public class Tarea
/*  22:    */   extends EntidadBase
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 6091163816189186410L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="tarea", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tarea")
/*  28:    */   @Column(name="id_tarea")
/*  29:    */   private int idTarea;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @Column(name="codigo", nullable=false, length=10)
/*  35:    */   @NotNull
/*  36:    */   @Size(min=2, max=10)
/*  37:    */   private String codigo;
/*  38:    */   @Column(name="nombre", nullable=false, length=50)
/*  39:    */   @NotNull
/*  40:    */   @Size(min=2, max=50)
/*  41:    */   private String nombre;
/*  42:    */   @Column(name="descripcion", length=200)
/*  43:    */   @Size(max=200)
/*  44:    */   private String descripcion;
/*  45:    */   @Column(name="activo", nullable=false)
/*  46:    */   private boolean activo;
/*  47:    */   @Column(name="numero_personas", nullable=false)
/*  48:    */   @NotNull
/*  49:    */   @Min(0L)
/*  50:    */   @Max(999999999L)
/*  51:    */   private int numeroPersonas;
/*  52:    */   @Column(name="duracion", nullable=false)
/*  53:    */   @NotNull
/*  54:    */   @Min(0L)
/*  55:    */   @Max(999999999L)
/*  56:    */   private int duracion;
/*  57:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_actividad", nullable=true)
/*  59:    */   private Actividad actividad;
/*  60:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  61:    */   @JoinColumn(name="id_tarifa_salarial", nullable=true)
/*  62:    */   private TarifaSalarial tarifaSalarial;
/*  63:    */   
/*  64:    */   public int getIdTarea()
/*  65:    */   {
/*  66:117 */     return this.idTarea;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdTarea(int idTarea)
/*  70:    */   {
/*  71:127 */     this.idTarea = idTarea;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String getCodigo()
/*  75:    */   {
/*  76:136 */     return this.codigo;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setCodigo(String codigo)
/*  80:    */   {
/*  81:146 */     this.codigo = codigo;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getNombre()
/*  85:    */   {
/*  86:155 */     return this.nombre;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setNombre(String nombre)
/*  90:    */   {
/*  91:165 */     this.nombre = nombre;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getDescripcion()
/*  95:    */   {
/*  96:174 */     return this.descripcion;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setDescripcion(String descripcion)
/* 100:    */   {
/* 101:184 */     this.descripcion = descripcion;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean isActivo()
/* 105:    */   {
/* 106:193 */     return this.activo;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setActivo(boolean activo)
/* 110:    */   {
/* 111:203 */     this.activo = activo;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getNumeroPersonas()
/* 115:    */   {
/* 116:212 */     return this.numeroPersonas;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setNumeroPersonas(int numeroPersonas)
/* 120:    */   {
/* 121:222 */     this.numeroPersonas = numeroPersonas;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public int getDuracion()
/* 125:    */   {
/* 126:231 */     return this.duracion;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setDuracion(int duracion)
/* 130:    */   {
/* 131:241 */     this.duracion = duracion;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Actividad getActividad()
/* 135:    */   {
/* 136:250 */     return this.actividad;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setActividad(Actividad actividad)
/* 140:    */   {
/* 141:260 */     this.actividad = actividad;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public TarifaSalarial getTarifaSalarial()
/* 145:    */   {
/* 146:269 */     return this.tarifaSalarial;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setTarifaSalarial(TarifaSalarial tarifaSalarial)
/* 150:    */   {
/* 151:279 */     this.tarifaSalarial = tarifaSalarial;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public int getIdOrganizacion()
/* 155:    */   {
/* 156:288 */     return this.idOrganizacion;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setIdOrganizacion(int idOrganizacion)
/* 160:    */   {
/* 161:298 */     this.idOrganizacion = idOrganizacion;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public int getIdSucursal()
/* 165:    */   {
/* 166:307 */     return this.idSucursal;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setIdSucursal(int idSucursal)
/* 170:    */   {
/* 171:317 */     this.idSucursal = idSucursal;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public int getId()
/* 175:    */   {
/* 176:327 */     return this.idTarea;
/* 177:    */   }
/* 178:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.old.Tarea
 * JD-Core Version:    0.7.0.1
 */