/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.JoinColumn;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.validation.constraints.NotNull;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="usuario_organizacion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_usuario", "id_organizacion"})})
/*  17:    */ public class UsuarioOrganizacion
/*  18:    */   extends EntidadBase
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = -2984468711000714833L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="usuario_organizacion", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="usuario_organizacion")
/*  24:    */   @Column(name="id_usuario_organizacion")
/*  25:    */   private int idUsuarioOrganizacion;
/*  26:    */   @ManyToOne
/*  27:    */   @JoinColumn(name="id_usuario", nullable=false)
/*  28:    */   @NotNull
/*  29:    */   private EntidadUsuario entidadUsuario;
/*  30:    */   @ManyToOne
/*  31:    */   @JoinColumn(name="id_organizacion", nullable=false)
/*  32:    */   @NotNull
/*  33:    */   private Organizacion organizacion;
/*  34:    */   @ManyToOne
/*  35:    */   @JoinColumn(name="id_visualizacion", nullable=true)
/*  36:    */   private Visualizacion visualizacion;
/*  37:    */   @Column(name="predeterminado", nullable=false)
/*  38:    */   @NotNull
/*  39:    */   private boolean predeterminado;
/*  40:    */   
/*  41:    */   public int getId()
/*  42:    */   {
/*  43: 80 */     return this.idUsuarioOrganizacion;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public EntidadUsuario getEntidadUsuario()
/*  47:    */   {
/*  48: 89 */     return this.entidadUsuario;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setEntidadUsuario(EntidadUsuario entidadUsuario)
/*  52:    */   {
/*  53: 99 */     this.entidadUsuario = entidadUsuario;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean isPredeterminado()
/*  57:    */   {
/*  58:108 */     return this.predeterminado;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setPredeterminado(boolean predeterminado)
/*  62:    */   {
/*  63:118 */     this.predeterminado = predeterminado;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int getIdUsuarioOrganizacion()
/*  67:    */   {
/*  68:122 */     return this.idUsuarioOrganizacion;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdUsuarioOrganizacion(int idUsuarioOrganizacion)
/*  72:    */   {
/*  73:126 */     this.idUsuarioOrganizacion = idUsuarioOrganizacion;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Organizacion getOrganizacion()
/*  77:    */   {
/*  78:130 */     return this.organizacion;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setOrganizacion(Organizacion organizacion)
/*  82:    */   {
/*  83:134 */     this.organizacion = organizacion;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Visualizacion getVisualizacion()
/*  87:    */   {
/*  88:138 */     return this.visualizacion;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setVisualizacion(Visualizacion visualizacion)
/*  92:    */   {
/*  93:142 */     this.visualizacion = visualizacion;
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.UsuarioOrganizacion
 * JD-Core Version:    0.7.0.1
 */