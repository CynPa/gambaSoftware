/*   1:    */ package com.asinfo.as2.entities.nomina.asistencia;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.entities.Rubro;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ import org.hibernate.annotations.ColumnDefault;
/*  18:    */ 
/*  19:    */ @Entity
/*  20:    */ @Table(name="tipo_falta", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo", "nombre", "id_rubro"})})
/*  21:    */ public class TipoFalta
/*  22:    */   extends EntidadBase
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="tipo_falta", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="tipo_falta")
/*  28:    */   @Column(name="id_tipo_falta")
/*  29:    */   private int idTipoFalta;
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
/*  42:    */   @Column(name="descripcion", nullable=true, length=200)
/*  43:    */   @Size(max=200)
/*  44:    */   private String descripcion;
/*  45:    */   @Column(name="activo", nullable=false)
/*  46:    */   private boolean activo;
/*  47:    */   @Column(name="numero_dias_falta", nullable=false)
/*  48:    */   @ColumnDefault("0")
/*  49:    */   @NotNull
/*  50:    */   private int numeroDiasFalta;
/*  51:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  52:    */   @JoinColumn(name="id_rubro", nullable=true)
/*  53:    */   private Rubro rubro;
/*  54:    */   
/*  55:    */   public int getId()
/*  56:    */   {
/*  57: 81 */     return this.idTipoFalta;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getIdTipoFalta()
/*  61:    */   {
/*  62: 85 */     return this.idTipoFalta;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setIdTipoFalta(int idTipoFalta)
/*  66:    */   {
/*  67: 89 */     this.idTipoFalta = idTipoFalta;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdOrganizacion()
/*  71:    */   {
/*  72: 93 */     return this.idOrganizacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdOrganizacion(int idOrganizacion)
/*  76:    */   {
/*  77: 97 */     this.idOrganizacion = idOrganizacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdSucursal()
/*  81:    */   {
/*  82:101 */     return this.idSucursal;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdSucursal(int idSucursal)
/*  86:    */   {
/*  87:105 */     this.idSucursal = idSucursal;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getCodigo()
/*  91:    */   {
/*  92:109 */     return this.codigo;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setCodigo(String codigo)
/*  96:    */   {
/*  97:113 */     this.codigo = codigo;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getNombre()
/* 101:    */   {
/* 102:117 */     return this.nombre;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setNombre(String nombre)
/* 106:    */   {
/* 107:121 */     this.nombre = nombre;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getDescripcion()
/* 111:    */   {
/* 112:125 */     return this.descripcion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setDescripcion(String descripcion)
/* 116:    */   {
/* 117:129 */     this.descripcion = descripcion;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public boolean isActivo()
/* 121:    */   {
/* 122:133 */     return this.activo;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setActivo(boolean activo)
/* 126:    */   {
/* 127:137 */     this.activo = activo;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Rubro getRubro()
/* 131:    */   {
/* 132:141 */     return this.rubro;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setRubro(Rubro rubro)
/* 136:    */   {
/* 137:145 */     this.rubro = rubro;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int getNumeroDiasFalta()
/* 141:    */   {
/* 142:149 */     return this.numeroDiasFalta;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setNumeroDiasFalta(int numeroDiasFalta)
/* 146:    */   {
/* 147:153 */     this.numeroDiasFalta = numeroDiasFalta;
/* 148:    */   }
/* 149:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.nomina.asistencia.TipoFalta
 * JD-Core Version:    0.7.0.1
 */