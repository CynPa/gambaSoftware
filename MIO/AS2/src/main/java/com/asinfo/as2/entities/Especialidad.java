/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.Table;
/*  10:    */ import javax.persistence.TableGenerator;
/*  11:    */ import javax.validation.constraints.Digits;
/*  12:    */ import javax.validation.constraints.NotNull;
/*  13:    */ import javax.validation.constraints.Size;
/*  14:    */ import org.hibernate.annotations.ColumnDefault;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="especialidad", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  18:    */ public class Especialidad
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 2809123092449642782L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="especialidad", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="especialidad")
/*  25:    */   @Column(name="id_especialidad")
/*  26:    */   private int idEspecialidad;
/*  27:    */   @Column(name="id_sucursal", nullable=false)
/*  28:    */   private int idSucursal;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="codigo", nullable=false, length=10)
/*  32:    */   @NotNull
/*  33:    */   @Size(min=2, max=10)
/*  34:    */   private String codigo;
/*  35:    */   @Column(name="nombre", nullable=false, length=50)
/*  36:    */   @NotNull
/*  37:    */   @Size(min=2, max=50)
/*  38:    */   private String nombre;
/*  39:    */   @Column(name="descripcion", nullable=true, length=200)
/*  40:    */   @Size(max=200)
/*  41:    */   private String descripcion;
/*  42:    */   @Column(name="activo", nullable=false)
/*  43:    */   private boolean activo;
/*  44:    */   @Column(name="predeterminado", nullable=false)
/*  45:    */   private boolean predeterminado;
/*  46:    */   @Column(name="costo_hora_hombre", nullable=true)
/*  47:    */   @Digits(integer=12, fraction=2)
/*  48:    */   @NotNull
/*  49:    */   @ColumnDefault("0")
/*  50: 76 */   private BigDecimal costoHoraHombre = BigDecimal.ZERO;
/*  51:    */   
/*  52:    */   public Especialidad() {}
/*  53:    */   
/*  54:    */   public Especialidad(int idEspecialidad, String codigo, String nombre)
/*  55:    */   {
/*  56: 96 */     this.idEspecialidad = idEspecialidad;
/*  57: 97 */     this.codigo = codigo;
/*  58: 98 */     this.nombre = nombre;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getId()
/*  62:    */   {
/*  63:108 */     return this.idEspecialidad;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdEspecialidad()
/*  67:    */   {
/*  68:120 */     return this.idEspecialidad;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdEspecialidad(int idEspecialidad)
/*  72:    */   {
/*  73:130 */     this.idEspecialidad = idEspecialidad;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getIdSucursal()
/*  77:    */   {
/*  78:139 */     return this.idSucursal;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdSucursal(int idSucursal)
/*  82:    */   {
/*  83:149 */     this.idSucursal = idSucursal;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int getIdOrganizacion()
/*  87:    */   {
/*  88:158 */     return this.idOrganizacion;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setIdOrganizacion(int idOrganizacion)
/*  92:    */   {
/*  93:168 */     this.idOrganizacion = idOrganizacion;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getCodigo()
/*  97:    */   {
/*  98:177 */     return this.codigo;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setCodigo(String codigo)
/* 102:    */   {
/* 103:187 */     this.codigo = codigo;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getNombre()
/* 107:    */   {
/* 108:196 */     return this.nombre;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setNombre(String nombre)
/* 112:    */   {
/* 113:206 */     this.nombre = nombre;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getDescripcion()
/* 117:    */   {
/* 118:215 */     return this.descripcion;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setDescripcion(String descripcion)
/* 122:    */   {
/* 123:225 */     this.descripcion = descripcion;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public boolean isActivo()
/* 127:    */   {
/* 128:234 */     return this.activo;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setActivo(boolean activo)
/* 132:    */   {
/* 133:244 */     this.activo = activo;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean isPredeterminado()
/* 137:    */   {
/* 138:253 */     return this.predeterminado;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setPredeterminado(boolean predeterminado)
/* 142:    */   {
/* 143:263 */     this.predeterminado = predeterminado;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public BigDecimal getCostoHoraHombre()
/* 147:    */   {
/* 148:272 */     return this.costoHoraHombre;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setCostoHoraHombre(BigDecimal costoHoraHombre)
/* 152:    */   {
/* 153:282 */     this.costoHoraHombre = costoHoraHombre;
/* 154:    */   }
/* 155:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Especialidad
 * JD-Core Version:    0.7.0.1
 */